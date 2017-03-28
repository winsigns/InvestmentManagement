
/**
 * author:fulin.wang at 2017-03-08
 * router config
 */
//引入模块
import Fund from "../page/fund/Fund.vue"
import FundCreate from "../page/fund/FundCreate.vue"
import FundProperties from "../page/fund/FundProperties.vue"
import FundCrecaAccountInfo from "../page/fund/FundCrecaAccountInfo.vue"

let defaultPath = '/Fund'
let routes = [
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
                    path: '/FundProperties/:fundId',
                    component: FundProperties,
                    name: 'FundProperties'
                },
                {
                    path: '/FundCrecaAccountInfo/:fundCreacaAccountId',
                    component: FundCrecaAccountInfo,
                    name: 'FundCrecaAccountInfo'
                }
            ]

    routes = routes.concat([{
        path: '/',
        redirect: defaultPath
    }, {
        path: '*',
        redirect: defaultPath
    }]);
export default routes;



