/**
 * author:fulin.wang at 2017-03-08
 * app main
 */
import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import NProgress from 'nprogress' //页面顶部进度条
import 'nprogress/nprogress.css'
import VueI18n from 'vue-i18n'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'

import store from './vuex/store'
import Vuex from 'vuex'
//css
import style from "./assets/style/style.css"
//js
import routerConfig from './router/router.config.js'
import wslang from './common/lang.js'
import ds from './common/ds'
import api from './config/api.json'
// import wsocket from './common/websocket/websocket'
 
Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(VueI18n)
Vue.use(Vuex)
/*wsocket.connect();*/
Vue.prototype.winsigns = {
    ds: ds,
    api: api,
    websocket:{
        // wsocket: wsocket,
        // wsocket_con: new wsocket.connect()
    }
};
//实例化I18n
var i18n = new VueI18n({
    locale: wslang.readLanguage(),
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
