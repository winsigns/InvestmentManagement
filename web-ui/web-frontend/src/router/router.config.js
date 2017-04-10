
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
                    path: '/fund',
                    component: Fund,
                    name: 'fund'
                },
                {
                    path: '/fund-create',
                    component: FundCreate,
                    name: 'fund-create'
                },
                {
                    path: '/fund-properties/:fundId',
                    component: FundProperties,
                    name: 'fund-properties'
                },
                {
                    path: '/fund-creca-accountInfo/:fundCreacaAccountId',
                    component: FundCrecaAccountInfo,
                    name: 'fund-creca-account-info'
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



