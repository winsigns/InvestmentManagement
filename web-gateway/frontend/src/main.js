/**
 * author:fulin.wang at 2017-03-08
 * app main
 */
import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
import NProgress from 'nprogress' //页面顶部进度条
import 'nprogress/nprogress.css'
import VueI18n from 'vue-i18n'
import locale from 'element-ui/lib/locale/lang/en'
//css
import style from "./assets/style/style.css"
//js
import routerConfig from './router/router.config.js'
import wslang from './common/lang.js'

Vue.use(ElementUI, { locale })
Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(VueI18n)

//设置语言:在util.js里有方法去设置语言， 但在这里不想引入太多的模块，所以就单独设置了
var lang = wslang.readLanguage();
document.body.lang = lang;
//实例化I18n
var i18n = new VueI18n({
    locale: lang,
    messages: wslang.getLanguageLib()
})
const routes = routerConfig;
const router = new VueRouter({
    routes
})

router.beforeEach((to, from, next) => {
    NProgress.start();
    next()
})

router.afterEach(transition => {
    NProgress.done();
});

//启动app
new Vue({
    el: '#app',
    template: '<App/>',
    router,
    store,
    components: { App },
    i18n: i18n
}).$mount('#app')
