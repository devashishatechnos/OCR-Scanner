package com.example.myapplication.activity

class MyScanner
{
    // property (data member)
    private var name: String = "Scanner"

    // member function
    fun printMe() {
        print("Scan Your Image-"+name)
    }
}
fun main(args: Array<String>) {
    val obj = MyScanner()
    obj.printMe()

    val demo = Outer.Nested().foo()
    print(demo)
}
class Outer {
    class Nested {
        fun foo() = "Welcome Image"
    }
}