
/**
 * author:fulin.wang at 2017-03-15
 * language
 */
//引入模块
import { use } from 'element-ui/lib/locale';
import zhLocale from 'element-ui/lib/locale/lang/zh-CN';
import enLocale from 'element-ui/lib/locale/lang/en'
import zh from '../config/lang/lang/zh.json'
import en from '../config/lang/lang/en.json'

export default {
    getLanguageLib: function(){
        return {
            zh: {
                message: zh
            },
            en: {
                message: en
            }
        }
    },
    setLanguage:function(lang){
        var local = zhLocale;
        if (lang === 'en'){
            local = enLocale
        }
        use(local);
        document.body.lang=lang;
        window.localStorage.setItem('winsigns_language', lang);
    },
    readLanguage: function(){
        return window.localStorage.getItem('winsigns_language')?window.localStorage.getItem('winsigns_language'):'zh'     
    }
}

