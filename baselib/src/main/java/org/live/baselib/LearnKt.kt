package sample.kt

import java.io.BufferedReader
import java.io.File
// 重命名
import java.io.FileReader as FF

/**
 * kotlin的灵活
 * 1. 文件名称可以随便定义
 * 2. 一个文件可以多个类
 * 3. package sample.kt可以和文件层级不同(随便写),但是一个.kt文件也只能定义一次
 */

const val UNIX_LINE = "\n"
/**
 * 顶层函数和顶层变量都会生成java LearnKtKt的 静态方法和 静态变量
 * const val 生成 public static final 常量
 * val 生成 private static final String name 并生成getName方法
 * val 生成 private static String name 并生成 getter + setter 方法
 * fun say 生 public static say()
 *
    public final class LearnKtKt {
        @NotNull
        public static final String UNIX_LINE = "\n";
    }
 */

fun main(args: Array<String>) {
    // 表达式函数体
    val result = max(10, 5)
    println(result)

    // 不可变类型
    val temp: String
    temp = "I just can define onece"

    // 字符串模板
    println("hello ${if(10 > 0) "java" else "python"}")

    sampleWhenX(10, 1)
    sampleWhenX(1, 10)

    // 智能转换(等价于Java的instanceOf,方法体不需要再强制转换了，多态)
    var p = Person("xx")
    if (p is Person) {
        println(p.name)
    }

    // while 循环，完全等价Java
    var i = 0
    while (i < 10) {
        println("execute me")
        i++
    }

    // for循环（kotlin没有常规的for循环，而是用区间代替）
    for (jj in 1..20) {
        println("==> ${jj}")
    }

    for (jjj in 1..20 step 2) {
        println("==> ${jjj}")
    }

    // 迭代map
    val map = HashMap<Char, String>()
    // 先预存到map中一些数据
    for (key in 'A'..'F') {
        val value = Integer.toBinaryString(key.toInt())
        map[key] = value
    }
    for ((key, value) in map) {
        println("$key = $value")
    }

    // 遍历list并附带下标
    val list = arrayListOf<String>("11", "22", "33")
    for ((index, element) in list.withIndex()) {
        println("$index : $element")
    }

    // in 还可以用作检查是否存在某个成员(与for含义不同)
    if (1 in 1..10) {
        println("1 在区间内")
    }

    // 集合
    val set = setOf<String>("java", "c++")
    println(set)

    //  异常
    val value = if (10 > 20) {
        // 返回字符串
        "10 > 0"
    } else {
        // 直接抛出异常
        // throw RuntimeException("have a ex")
        // 返回异常对象
        RuntimeException("have a ex")
    }
    println(value)

    // try 表达式
    val readNum = try {
        var reader = BufferedReader(FF(File("D:/test.txt")))
        val msg = reader.readLine()
        Integer.parseInt(msg)
    } catch (e: NumberFormatException) {
        println(e)
        null
    }
    println(readNum)

    // 集合
    val hashset = hashSetOf(1, 2, "33", Person("dongdong"))
    // to 是一个中缀表达式
    val hashmap = hashMapOf(1 to "one", 7 to "seven")
    // hashmap.javaClass ==> java.getClass()
    println(hashmap.javaClass)
    hashset.first()
    hashset.last()

    // joinToString()
    val listX = listOf<Int>(1, 2, 3)
    // [1, 2, 3]
    println(listX)
    val str = listX.joinToString(separator = " ", prefix = "==>", postfix = "<==")
    // ==>1 2 3<==
    println(str)

    // 可变参数
    argFun(1, 2, 3, "fuck")

    // 中缀表达式和解构声明
    val pair = "name" nono "nonolive"
    println("${pair.first} ${pair.second}")


}

/**
 * 顶层函数,在Java中调用方式: LearnKtKt.printKt()
 * 等价于Java的静态方法
 */
fun printKt() {
    println("I am kotlin")
}
/**
 * 生成的样板代码如下:
    public final class LearnKtKt {
        public static final void printKt() {
            String var0 = "I am kotlin";
            System.out.println(var0);
        }
     }
 */



/**
 * 表达式函数体，类型推断，只有表达式可以省略返回值
 */
fun max(a: Int,b:Int) = if(a > b) a else b

/**
 * 作值对象
 * 如果val 属性，只生成getXX 方法
 * 如果var属性，生成get 和 set 方法
 */
class Person(val name: String)
/**
    public final class Person {
        @NotNull
        private final String name;

        @NotNull
        public final String getName() {
            return this.name;
        }

        public Person(@NotNull String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            super();
            this.name = name;
        }
    }
*/

/**
 * 自定义属性的get或set方法
 */
class Cat(val name: String){
    var age: Int
        // 可以简写为表达式
        get() = if(true) 20 else 0
        set(num: Int) {
            age = num
        }
}

/**
 * 枚举1
 */
enum class Color {
    RED,BLUE,GREEN
}

/**
 * 枚举2 (同Java，枚举也可以有属性和方法)
 */
enum class ColorX(val r: Int,val g: Int, val b: Int) {
    RED(255,0,0),
    YELLOW(255,255,0),
    BLUE(0,0,222),
    GREEN(33,33,33);

    fun rgb(): Int {
        return (r * 256 + g) * 256 + b
    }
}

fun sampleWhen(color: Color) {
    var color = when (color) {
        Color.BLUE -> "1"
        Color.RED, Color.GREEN, Color.GREEN -> "2"
    }
    println(color)
}

/**
 * 不带参数的when，更方便
 */
fun  sampleWhenX(a: Int, b: Int){
    when {
        a > b -> println("=>a")
        a < b -> println("=>b")
        else -> {
            throw Exception("Dirty value")
        }
    }
}

/**
 * 扩展函数
 * 扩展函数并不能访问私有的或者受保护的成员(因为扩展函数的生成原理)
 * 原理受限：不允许打破原有类的封装性
 * 扩展函数其实就是静态方法，只不过第一个参数是调用者对象
 *
 * 扩展函数不支持重写，原因也是它本质是一个静态的Java工具方法
 */
fun String.lastChar(): Char = get(length - 1)

/**
 * 扩展属性
 * 必须定义getter函数，因为它不属于真正的属性，所以不会自动生成
 */
val String.lastCharMe: Char
    get() = get(length - 1)

fun <T> argFun(vararg values: T){
    // T 表示values中元素的类型
    println(values.javaClass)

    for (temp in values) {
        println(temp)
    }
}

/**
 * 中缀表达式和解构声明
 * 解构声明的语法就是: 把Pair(1, "one") 拆成 val(number, name)的语法
 *
 * 而中缀表达式则是把 形如 obj.to("one")的语法 变成 obj to "one" 省略了.
 * 中缀表达式很简单，且与只有一个参数的函数一起使用，是函数调用的省略写法
 *
 * infix修饰符标记函数即可
 */
infix fun Any.nono(other: Any): Pair<Any, Any> {
    // 使用 "a" nono "b" 生成一个Pair对象
    return Pair(this, other)
}