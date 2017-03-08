
//专门配置路由规则

//引入模块
import Login from './components/Login.vue'
import Home from './components/Home.vue'
import Main from './components/Main.vue'
import Table from './components/nav1/Table.vue'
import Form from './components/nav1/Form.vue'
import Page3 from './components/nav1/Page3.vue'
import Page4 from './components/nav2/Page4.vue'
import Page5 from './components/nav2/Page5.vue'
import Page6 from './components/nav3/Page6.vue'
import echarts from './components/charts/echarts.vue'

module.exports =
     [
         {
            path: '/login',
            component: Login,
            hidden: true //不显示在导航中
        },
       /* { path: '/main', component: Main },*/
        {
            path: '/',
            component: Home,
            name: '导航一',
            iconCls: 'el-icon-message', //图标样式class
            children: [
                //{ path: '/main', component: Main },
                { path: '/table', component: Table, name: 'Table' },
                { path: '/form', component: Form, name: 'Form' },
                { path: '/page3', component: Page3, name: '页面3' },
            ]
        },
        {
            path: '/',
            component: Home,
            name: '导航二',
            iconCls: 'fa fa-id-card-o',
            children: [
                { path: '/page4', component: Page4, name: '页面4' },
                { path: '/page5', component: Page5, name: '页面5' }
            ]
        },
        {
            path: '/',
            component: Home,
            name: '',
            iconCls: 'fa fa-address-card',
            leaf: true, //只有一个节点
            children: [
                { path: '/page6', component: Page6, name: '导航三' }
            ]
        },
        {
            path: '/',
            component: Home,
            name: 'Charts',
            iconCls: 'fa fa-bar-chart',
            children: [
                { path: '/echarts', component: echarts, name: 'echarts' }
            ]
        },
        {
            path: '/',
            component: Home,
            name: '投资决策',
            iconCls: 'fa fa-bar-chart',
            children: [
                { path: '/echarts', component: echarts, name: 'echarts' }
            ]
        },
        {
            path: '/',
            component: Home,
            name: '投资决策二',
            iconCls: 'fa fa-bar-chart',
            children: [
                { path: '/echarts', component: echarts, name: 'echarts' }
            ]
        }
    ]


