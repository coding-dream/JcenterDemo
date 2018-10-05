package sample.kt2

fun main(args: Array<String>) {
    commonMethod()
}

/**
 * 常用的一些方法
 */
fun commonMethod() {
    val love = "love me"
    // 转换为首字符大写
    println(love.capitalize())
    // 转换为首字符小写
    println(love.decapitalize())
}
