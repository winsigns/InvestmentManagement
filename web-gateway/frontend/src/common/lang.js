
/**
 * author:fulin.wang at 2017-03-15
 * language
 */
//引入模块
import zh from '../config/lang/lang/zh-cn.json'
import en from '../config/lang/lang/en.json'
import ja from '../config/lang/lang/ja.json'

export default {
    getLanguageLib: function(){
        return {
            zh: {
                message: zh
            },
            en: {
                message: en
            },
            ja: {
                message: ja
            }
        }
    },
    setLanguage:function(lang){
        document.body.lang=lang;
        window.localStorage.setItem('winsigns_language', lang);
    },
    readLanguage: function(){
        return window.localStorage.getItem('winsigns_language')?window.localStorage.getItem('winsigns_language'):'zh'     
    }
}

