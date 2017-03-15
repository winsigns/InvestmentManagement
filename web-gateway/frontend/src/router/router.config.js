
/**
 * author:fulin.wang at 2017-03-08
 * router config
 */
//引入模块
import Fund from "../page/fund/Fund.vue"
import FundCreate from "../page/fund/FundCreate.vue"
import FundDetail from "../page/fund/FundDetail.vue"

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


