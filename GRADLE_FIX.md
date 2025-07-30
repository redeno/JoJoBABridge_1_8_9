# Исправление проблем с Gradle 8+

## ❌ Проблема
```
Using insecure protocols with repositories, without explicit opt-in, is unsupported.
```

## ✅ Решение

### Вариант 1: Автоматическое исправление
```bash
./fix-gradle.sh
```

### Вариант 2: Ручное исправление

1. **Обновите gradle.properties:**
```properties
org.gradle.jvmargs=-Xmx3G
org.gradle.daemon=false
org.gradle.parallel=false
org.gradle.configureondemand=false
```

2. **Создайте совместимый wrapper:**
```bash
gradle wrapper --gradle-version 7.6.4
```

3. **Используйте wrapper для сборки:**
```bash
./gradlew setupDecompWorkspace
./gradlew build
```

### Вариант 3: Использование Java 8
```bash
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
./build-mod.sh
```

## 📋 Что было исправлено

- ✅ Заменен `jcenter()` на `gradlePluginPortal()` и `mavenCentral()`
- ✅ Добавлен `allowInsecureProtocol = true` для HTTP репозиториев
- ✅ Обновлена версия ForgeGradle с 2.1 до 2.3
- ✅ Обновлены mappings с stable_20 до stable_22
- ✅ Добавлены настройки совместимости для современных версий Gradle

## 🎯 Результат
После исправления вы сможете собрать мод командой:
```bash
./gradlew build
```

Готовый файл будет в `build/libs/chatbridge-1.0.0.jar`