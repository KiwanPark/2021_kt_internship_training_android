package contract

import javax.security.auth.callback.Callback

interface Contract {
    interface Presenter {
        interface News {
            fun searchNews(query:String, searchStartPosition:Int, callback: (isSuccess:Boolean, data:Any?)->Unit)
        }
    }
}