/** 
 * author:fulin.wang
 * date:2017-03-09
*/
// see http://vuejs-templates.github.io/webpack for documentation.
var path = require('path')

const dataURL = 'http://localhost:8088';//http://180.168.43.246:8897/'; //开发环境中数据服务器地址

module.exports = {
    build: {
        env: require('./prod.env'),
        index: path.resolve(__dirname, '../dist/index.html'),
        assetsRoot: path.resolve(__dirname, '../dist'),
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        productionSourceMap: true,
        productionGzip: false,
        productionGzipExtensions: ['js', 'css']
    },
    dev: {
        env: require('./dev.env'),
        port: 8089,
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        proxyTable: {            
            '/fund-service': {
                target: dataURL,
                changeOrigin: true,
                pathRewrite: {
                    '^/fund-service': 'fund-service'
                }
            },
            '/inventory-service': {
                target: dataURL,
                changeOrigin: true,
                pathRewrite: {
                    '^/inventory-service': 'inventory-service'
                }
            }     
        },
        cssSourceMap: false
    }
}