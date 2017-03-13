
/**
 * author:fulin.wang at 2017-03-08
 * router config
 */
//引入模块
 import Fund from "./components/fund/Fund.vue"
 import FundCreate from "./components/fund/FundCreate.vue"
import FundDetail from "./components/fund/FundDetail.vue"

module.exports =
     [
        {
            path: '/Fund',
            component: Fund,
            name: 'Fund'
        },
        {
            path: '/FundCreate',
            component: FundCreate,
            name: 'FundCreate'           
        },
        {
            path: '/FundDetail/:fundId',
            component: FundDetail,
            name: 'FundDetail'           
        }        
    ]


