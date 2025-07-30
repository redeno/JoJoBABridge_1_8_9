#!/bin/bash

echo "=== Chat Bridge Mod - Современная сборка ==="

# Проверяем Java
if ! command -v java &> /dev/null; then
    echo "❌ Java не найдена! Установите Java 8 или выше."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
echo "✅ Java версия: $JAVA_VERSION"

# Проверяем Gradle
if ! command -v gradle &> /dev/null; then
    echo "❌ Gradle не найден! Установите Gradle или используйте gradle wrapper."
    exit 1
fi

GRADLE_VERSION=$(gradle --version | grep "Gradle" | cut -d' ' -f2)
echo "✅ Gradle версия: $GRADLE_VERSION"

# Используем современный build файл
echo "🔧 Используем современную конфигурацию сборки..."
cp build-modern.gradle build.gradle

# Создаем gradle wrapper если его нет
if [ ! -f "gradlew" ]; then
    echo "🔧 Создаем Gradle Wrapper..."
    gradle wrapper --gradle-version 7.6.4
fi

# Настройка рабочего пространства
echo "🔧 Настройка рабочего пространства..."
./gradlew setupDecompWorkspace

if [ $? -ne 0 ]; then
    echo "❌ Ошибка при настройке рабочего пространства"
    echo "💡 Попробуйте использовать Java 8:"
    echo "   export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64"
    echo "   export PATH=\$JAVA_HOME/bin:\$PATH"
    exit 1
fi

echo "✅ Рабочее пространство настроено"

# Сборка мода
echo "🔨 Сборка мода..."
./gradlew build

if [ $? -eq 0 ]; then
    echo "✅ Мод успешно собран!"
    echo "📦 Готовый файл: build/libs/chatbridge-1.0.0.jar"
    echo ""
    echo "🎮 Установка:"
    echo "1. Установите Minecraft Forge 1.8.9"
    echo "2. Скопируйте chatbridge-1.0.0.jar в папку mods"
    echo "3. Запустите Minecraft"
    echo ""
    echo "🎯 Команды в игре:"
    echo "- /bridge - открыть настройки"
    echo "- /bc <сообщение> - отправить в бридж чат"
else
    echo "❌ Ошибка при сборке мода"
    echo ""
    echo "💡 Возможные решения:"
    echo "1. Используйте Java 8 для Minecraft 1.8.9:"
    echo "   export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64"
    echo "2. Или попробуйте старый метод сборки:"
    echo "   ./build-mod.sh"
    exit 1
fi