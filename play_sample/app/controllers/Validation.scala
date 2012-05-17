package controllers

import play.api.data.Forms.mapping
import play.api.data.Forms.number
import play.api.data.Forms.text
import play.api.data.validation.Constraints.nonEmpty
import play.api.data.Form
import play.api.mvc.Action
import play.api.mvc.Controller
import play.Logger

object Validation extends Controller {

  //Formを定義
  val validationForm = Form(
    mapping(
      "par1" -> number,
      "par2" -> number)(DataX.apply)(DataX.unapply)
      //複数項目を使ったvalidation。DBアクセスしてのチェックもこのようにできる。
      verifying ("計算結果は0より大きい値でお願いします。", fields => fields match {
        case DataX(p1, p2) => DataX.isOverZero(p1, p2)
      })
      verifying ("計算結果は10未満でお願いします。", fields => fields match {
        case DataX(p1, p2) => DataX.isLessThan10(p1, p2)
      })      
  )

  def index = Action {
    Ok(views.html.validation.validation(validationForm))
  }

  def submit = Action { implicit request =>

    validationForm.bindFromRequest.
      fold(

        formWithErrors => {
          Logger.info("NG")

          BadRequest(views.html.validation.validation(formWithErrors))
        },
        validationForm => {
          Logger.info("OK")
          Redirect(routes.Validation.show(validationForm.par1, validationForm.par2))
        })
  }

  //submitして正常終了した時にRedirectで呼び出される処理
  def show(par1: Int, par2: Int) = Action { implicit requst =>
    Ok(views.html.validation.validation_show(par1, par2))
  }

}