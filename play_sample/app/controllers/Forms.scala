package controllers

case class User(name: String, age: Int)

class DataX(val par1: Int, val par2: Int, val calcResult: Int = 0)

//DataXクラスのコンパニオンオブジェクト
object DataX {
  def apply(par1: Int, par2: Int) : DataX = new DataX(par1, par2)
  def unapply(oz: DataX) = Some(oz.par1, oz.par2)
  
  /***
   * par1とpar2を掛けた結果は0よりも大きいこと
   */
  def isOverZero(par1:Int, par2:Int): Boolean = par1 * par2 > 0
   /***
   * par1とpar2を掛けた結果は10未満であること
   */
  def isLessThan10(par1:Int, par2:Int): Boolean = par1 * par2 < 10
  
}