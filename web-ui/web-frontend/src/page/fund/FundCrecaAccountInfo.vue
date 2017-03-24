<template> 
    <div>
        <el-row>
            <el-col :offset="5" :span="19">
                <div class="line_bottom">                
                    <h1>资金互转</h1>
                    <h3>当前外部资金帐号：{{fundCreaAccontInfoList.accountNo}}</h3>                             
                </div>                
                <div class="line_margin_top"></div>
            </el-col>            
        </el-row>
        <el-row>        
            <el-col :span="12" :offset="5">                 
                <el-table  :data="fundCreaAccontInfoCapList" style="width: 100%"
                stripe>
                    <el-table-column sortable
                        prop="currency"
                        label="币种">
                    </el-table-column>  
                    <el-table-column sortable
                        prop="unassignedCapital"
                        label="未分配">
                    </el-table-column>  
                    <el-table-column sortable label="现金" prop="ecma">
                    </el-table-column>                    
                    <el-table-column label="操作" sortable="false">
                        <template scope="scope">
                            <el-button
                            size="small"
                            type="text" @click="handleIn(scope.row)">资金调拨</el-button>                                                                                                          
                        </template>
                    </el-table-column>                 
                </el-table>
            </el-col>	                     
        </el-row>  

         <el-dialog title="资金转入" v-model="dlg.dlgVisible" size="tiny">
            <el-form :model="dlg" v-loading="loading" element-loading-text="提交中">
                <el-form-item label="对手方账户" label-width="140px">
                     <el-input v-model="dlg.dlgFundCreaAccount"></el-input>
                </el-form-item>                      
                 <el-form-item label="金额" label-width="140px">
                     <el-input v-model="dlg.dlgFundCreaMoney"></el-input>
                </el-form-item>          
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dlg.dlgVisible=false">取 消</el-button>
                <el-button @click="capitalIn()">确 定</el-button>
            </div>
        </el-dialog>    
    </div>
</template>
<script>
    import api from '../../config/api.json'
    import ds from '../../common/ds'
    import wsocket from '../../common/websocket/websocket'
    export default{
        data(){
            return {
                nextNum: 10,
                loading: false,
                timeCtrl: null,            
                fundCreaAccontInfoList:[],
                fundCreaAccontInfoCapList: [],
                //对话框  
                dlg:{
                    dlgTitle: '',
                    dlgFundCreaAccount: '',
                    dlgId: '',
                    dlgFundCreaMoney: '0',
                    dlgVisible: false
                }     
            }
        },
        mounted: function(){         
            var _self = this;

            _self.getFundCreaAccountsInfo(); 
            /*if (!_self.timeCtrl){
                _self.timeCtrl = setInterval(function(){
                    _self.getFundCreaAccountsInfo();
                },5000)
            }   */
        },
        methods:{
            handleIn: function(row){
                this.dlg.dlgVisible= true;
                this.dlg.dlgId = row.id;
            },
            getFundCreaAccountsInfo: function(){
                var _self = this;  
                ds.GET({url:api.fundURL.fundCreaAccounts + '/'+ _self.$route.params.fundCreacaAccountId,
                        data:{}},function(data){                    
                    _self.fundCreaAccontInfoList = data;  
                    _self.fundCreaAccontInfoCapList = [];
                    if ( data._embedded){
                        if (data._embedded["eca-cash-pools"]){                            
                            var tempData = data._embedded["eca-cash-pools"];  
                            for (var i=0;i<=tempData.length-1;i++){
                                    tempData[i].ecma =[]
                                    tempData[i].ecma=tempData[i].measureInfo?
                                        tempData[i].measureInfo["ECACashPoolMHT.ECACashMeasure"].value:[];
  
                                _self.fundCreaAccontInfoCapList.push(tempData[i]);
                            }
                            wsocket.connect(_self.fundCreaAccontInfoCapList,_self.moneySubscribe);
                        }                                                
                    }                                               
                })  
            },
            capitalIn: function(){
                var _self = this;
                _self.loading = true; 
                ds.POST({url:"inventory-service/eca-cash-pools/" + _self.dlg.dlgId +'/allot-in',
                        data:{"changedCapital":_self.dlg.dlgFundCreaMoney,
                        "matchECACashPoolId":_self.dlg.dlgFundCreaAccount},v:_self},function(data){
                        _self.loading = false; 
                        _self.getFundCreaAccountsInfo(); 
                        _self.dlg.dlgVisible= false;
                        _self.dlg.dlgFundCreaMoney="";
                        _self.$message({
                            message: '调拨成功',
                            type: 'success'
                        });                                             
                    })  
            },
            moneySubscribe: function(message){
                var _self=this;
                var tempData =  [];
                for (var i=0;i<=_self.fundCreaAccontInfoCapList.length-1;i++){
                    if (/*('/topic/ECACashPoolMHT/'+_self.fundCreaAccontInfoCapList[i].id+
                    '/ECACashMeasure'*/
                    '/topic/'+_self.fundCreaAccontInfoCapList[i].measureInfo["ECACashPoolMHT.ECACashMeasure"].key
                        ==message.headers.destination){
                        tempData.push({
                            'id':_self.fundCreaAccontInfoCapList[i].id,
                            'currency':_self.fundCreaAccontInfoCapList[i].currency,
                            'unassignedCapital':_self.fundCreaAccontInfoCapList[i].unassignedCapital,
                            'ecma':message.body
                        })
                    } else{
                        tempData.push({
                            'id':_self.fundCreaAccontInfoCapList[i].id,
                            'currency':_self.fundCreaAccontInfoCapList[i].currency,
                            'unassignedCapital':_self.fundCreaAccontInfoCapList[i].unassignedCapital,
                            'ecma':_self.fundCreaAccontInfoCapList[i].ecma
                        })
                    }
                }
                _self.fundCreaAccontInfoCapList = tempData;
            }
        },
        beforeDestroy: function(){
            if (this.timeCtrl){
                clearInterval(this.timeCtrl);
            }
        }
    }
</script>
<style scoped>
    .list-item {
        /*display: inline-block;*/
        margin-right: 10px;
    }
    .list-enter-active, .list-leave-active {
        transition: all 1s;
    }
    .list-enter, .list-leave-active {
        opacity: 0;

        transition: opacity 1s ease-in-out;
        color: red;
    }
</style>