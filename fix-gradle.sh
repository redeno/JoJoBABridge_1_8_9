#!/bin/bash

echo "=== Исправление проблем с Gradle ==="

# Проверяем версию Gradle
if command -v gradle &> /dev/null; then
    GRADLE_VERSION=$(gradle --version | grep "Gradle" | cut -d' ' -f2)
    echo "🔍 Найден Gradle версии: $GRADLE_VERSION"
    
    # Если версия Gradle 8+, используем обновленную конфигурацию
    if [[ $GRADLE_VERSION > "8" ]]; then
        echo "⚠️  Gradle 8+ обнаружен, применяем исправления..."
        
        # Создаем gradle.properties с необходимыми настройками
        cat > gradle.properties << EOF
org.gradle.jvmargs=-Xmx3G
org.gradle.daemon=false
org.gradle.parallel=false
org.gradle.configureondemand=false
EOF
        
        echo "✅ Создан gradle.properties с совместимыми настройками"
        
        # Пробуем создать wrapper с совместимой версией
        echo "🔧 Создаем Gradle Wrapper с версией 7.6.4..."
        gradle wrapper --gradle-version 7.6.4 --distribution-type all
        
        if [ $? -eq 0 ]; then
            echo "✅ Gradle Wrapper создан успешно"
            echo "💡 Теперь используйте ./gradlew вместо gradle"
            echo ""
            echo "🚀 Команды для сборки:"
            echo "  ./gradlew setupDecompWorkspace"
            echo "  ./gradlew build"
        else
            echo "❌ Не удалось создать wrapper"
            echo "💡 Попробуйте установить совместимую версию Java:"
            echo "  export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64"
            echo "  export PATH=\$JAVA_HOME/bin:\$PATH"
        fi
    else
        echo "✅ Версия Gradle совместима"
    fi
else
    echo "❌ Gradle не найден в системе"
    echo "💡 Установите Gradle или используйте существующий wrapper"
fi

echo ""
echo "📋 Варианты сборки:"
echo "1. Современный метод (если есть Gradle):"
echo "   ./gradlew setupDecompWorkspace && ./gradlew build"
echo ""
echo "2. Старый метод (с Java 8):"
echo "   export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64"
echo "   export PATH=\$JAVA_HOME/bin:\$PATH"
echo "   ./build-mod.sh"
echo ""
echo "3. Если ничего не работает:"
echo "   Используйте готовый jar файл из build/libs/"