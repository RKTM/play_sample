package controllers

import play.api.data.Forms.mapping
import play.api.data.Forms.number
import play.api.data.Forms.text
import play.api.data.validation.Constraints.nonEmpty
import play.api.data.Form
import play.api.mvc.Action
import play.api.mvc.Controller
import play.Logger

object FormSample extends Controller {

  //Formを定義
  val userForm = Form(
    mapping(
      "name" -> text.verifying(nonEmpty),
      "age" -> number)(User.apply)(User.unapply))

  //Formを表示
  def index = Action {
    Ok(views.html.sample_form(userForm))
  }

  //submitされた時に呼び出される処理
  def submit = Action { implicit request =>

    //def bindFromRequest ()(implicit request: play.api.mvc.Request[_]): Form[T]
    //リクエストのデータをformにバインドする。戻り値はそのform。
    userForm.bindFromRequest.

	    //def fold [R] (hasErrors: (Form[T]) ⇒ R, success: (T) ⇒ R): R
	    //R：リザルト（？）型
    	//hasErrors： エラーのあるformを処理する関数
    	//success：サブミットが成功したformを処理する関数
    	//returns：リザルトのR 
    	fold(

	        formWithErrors => {
	          Logger.info("NG")
	          //formでエラーがあった場合の処理
	          
	          BadRequest(views.html.sample_form(formWithErrors)) //400を返す
	        },
	        userForm => {
	          Logger.info("OK")
	          //ここで入力が正常に行われた場合の処理を行う。
	
	          //例：DB更新
	
	          //処理が終わったらリダイレクト
	          Redirect(routes.FormSample.show(userForm.name, userForm.age))
	        })
  }

  //submitして正常終了した時にRedirectで呼び出される処理
  def show(name: String, age: Int) = Action { implicit requst =>
    Ok(views.html.sample_form_show(name, age))
  }

}