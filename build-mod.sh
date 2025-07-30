#!/bin/bash

echo "=== Chat Bridge Mod - Автоматическая сборка ==="

# Проверяем Java 8
if ! command -v java &> /dev/null; then
    echo "❌ Java не найдена! Установите Java 8."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
if [[ ! $JAVA_VERSION == 1.8* ]]; then
    echo "⚠️  Внимание: Используется Java $JAVA_VERSION, но для Minecraft 1.8.9 рекомендуется Java 8"
    echo "Устанавливаем переменные окружения для Java 8..."
    export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
    export PATH=$JAVA_HOME/bin:$PATH
fi

echo "✅ Java версия: $(java -version 2>&1 | head -n 1)"

# Проверяем наличие Gradle
if [ ! -f "gradle-2.14/bin/gradle" ]; then
    echo "❌ Gradle 2.14 не найден! Убедитесь что он скачан и распакован."
    exit 1
fi

echo "✅ Gradle найден"

# Настройка рабочего пространства (если еще не настроено)
if [ ! -d ".gradle" ]; then
    echo "🔧 Настройка рабочего пространства..."
    gradle-2.14/bin/gradle setupDecompWorkspace
    if [ $? -ne 0 ]; then
        echo "❌ Ошибка при настройке рабочего пространства"
        exit 1
    fi
    echo "✅ Рабочее пространство настроено"
fi

# Сборка мода
echo "🔨 Сборка мода..."
gradle-2.14/bin/gradle build

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
    exit 1
fi