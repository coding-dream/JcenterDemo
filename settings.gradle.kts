// 1. 可以直接使用 setting.gradle.kts来替代setting.gradle文件而不需要额外任何配置
// 2. xx.gradle 和 xx.gradle.kts可以混合使用,就如同java和kotlin同存一样
// 3. 但是使用setting.gradle.kts在右侧栏还有些bug,故此处仍然保留原来的配置文件
rootProject.name = "nonoKt"
include("app","baselib")