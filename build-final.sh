#!/bin/bash

echo "=== Chat Bridge Mod - Финальная сборка ==="
echo "🔧 Автоматическое исправление проблем с Gradle..."

# Создаем совместимые настройки gradle.properties
cat > gradle.properties << 'EOF'
org.gradle.jvmargs=-Xmx3G
org.gradle.daemon=false
org.gradle.parallel=false
org.gradle.configureondemand=false
EOF

echo "✅ Настройки gradle.properties обновлены"

# Используем совместимый build.gradle
if [ -f "build-gradle4.gradle" ]; then
    cp build-gradle4.gradle build.gradle
    echo "✅ Использован совместимый build.gradle"
fi

# Обновляем gradle wrapper properties для Gradle 4.10.3
cat > gradle/wrapper/gradle-wrapper.properties << 'EOF'
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-4.10.3-all.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
EOF

echo "✅ Gradle wrapper обновлен на версию 4.10.3"

# Скачиваем правильный gradle-wrapper.jar
echo "🔄 Обновление gradle-wrapper.jar..."
wget -q -O gradle/wrapper/gradle-wrapper.jar https://github.com/gradle/gradle/raw/v4.10.3/gradle/wrapper/gradle-wrapper.jar

if [ $? -eq 0 ]; then
    echo "✅ gradle-wrapper.jar обновлен"
else
    echo "⚠️  Не удалось обновить gradle-wrapper.jar, используем существующий"
fi

# Проверяем Java
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1)
    echo "✅ Java найдена: $JAVA_VERSION"
else
    echo "❌ Java не найдена! Установите Java 8 или выше."
    exit 1
fi

# Создаем wrapper если нужно
echo "🔧 Создание Gradle wrapper..."
./gradlew wrapper --quiet

if [ $? -eq 0 ]; then
    echo "✅ Gradle wrapper готов"
else
    echo "⚠️  Wrapper уже существует или создался с предупреждениями"
fi

# Настройка workspace (если нужно)
if [ ! -d ".gradle/caches" ]; then
    echo "🔧 Настройка рабочего пространства..."
    ./gradlew setupDecompWorkspace --quiet
    
    if [ $? -eq 0 ]; then
        echo "✅ Рабочее пространство настроено"
    else
        echo "❌ Ошибка при настройке рабочего пространства"
        exit 1
    fi
else
    echo "✅ Рабочее пространство уже настроено"
fi

# Сборка мода
echo "🔨 Сборка мода..."
./gradlew build --quiet

if [ $? -eq 0 ]; then
    echo ""
    echo "🎉 Мод успешно собран!"
    echo "📦 Готовый файл: build/libs/chatbridge-1.0.0.jar"
    echo "📊 Размер файла: $(du -h build/libs/chatbridge-1.0.0.jar | cut -f1)"
    echo ""
    echo "🎮 Установка:"
    echo "1. Установите Minecraft Forge 1.8.9"
    echo "2. Скопируйте chatbridge-1.0.0.jar в папку mods"
    echo "3. Запустите Minecraft"
    echo ""
    echo "🎯 Команды в игре:"
    echo "- /bridge - открыть настройки"
    echo "- /bc <сообщение> - отправить в бридж чат"
    echo ""
    echo "✅ Проблема с Gradle 8+ решена!"
    echo "   Использован Gradle 4.10.3 + ForgeGradle 2.1"
else
    echo "❌ Ошибка при сборке мода"
    echo ""
    echo "💡 Попробуйте:"
    echo "1. Проверить Java версию: java -version"
    echo "2. Очистить кеш: rm -rf .gradle build"
    echo "3. Запустить заново: ./build-final.sh"
    exit 1
fi