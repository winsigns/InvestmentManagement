<template> 
    <div>
        <el-row>
            <el-col :offset="5" :span="19">
                <div class="line_bottom">                
                    <h1>{{ $t("message.fund.eca_cap_transfer") }}</h1>
                    <h3>{{ $t("message.fund.eca_no_curr") }}：{{fundCreaAccontInfoList.accountNo}}</h3>
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
                        :label=" $t('message.global.currency') ">
                    </el-table-column>  
                    <el-table-column sortable
                        prop="unassignedCapital"
                        :label=" $t('message.global.unallocated') ">
                    </el-table-column>  
                    <el-table-column sortable :label=" $t('message.global.ecma') "
                                     prop="ecma">
                    </el-table-column>                    
                    <el-table-column :label=" $t('message.global.operation') " sortable="false">
                        <template scope="scope">
                            <el-button
                            size="small"
                            type="text" @click="handleIn(scope.row)">{{ $t("message.fund.eca_cap_in") }}</el-button>
                        </template>
                    </el-table-column>                 
                </el-table>
            </el-col>	                     
        </el-row>  

         <el-dialog :title="$t('message.fund.eca_cap_in')" v-model="dlg.dlgVisible" size="tiny">
            <el-form :model="dlg" v-loading="loading" :element-loading-text=" $t('message.global.commit') ">
                <el-form-item :label=" $t('message.fund.eca_counter_acc') " label-width="140px">
                     <el-input v-model="dlg.dlgFundCreaAccount"></el-input>
                </el-form-item>                      
                 <el-form-item :label=" $t('message.global.amount') " label-width="140px">
                     <el-input v-model="dlg.dlgFundCreaMoney"></el-input>
                </el-form-item>          
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dlg.dlgVisible=false">{{ $t("message.global.cancel") }}</el-button>
                <el-button @click="capitalIn()">{{ $t("message.global.confirm") }}</el-button>
            </div>
        </el-dialog>    
    </div>
</template>
<script>
    import wsocket from '../../common/websocket/websocket'
    export default{
        data(){
            return {
                nextNum: 10,
                loading: false,
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
            this.getFundCreaAccountsInfo();
        },
        methods:{
            handleIn: function(row){
                this.dlg.dlgVisible= true;
                this.dlg.dlgId = row.id;
            },
            getFundCreaAccountsInfo: function(){
                var _self = this;
                _self.winsigns.ds.GET({url:_self.winsigns.api.fundURL.fundCreaAccounts + '/'+ _self.$route.params.fundCreacaAccountId,
                        data:{}},function(data){                    
                    _self.fundCreaAccontInfoList = data;  
                    _self.fundCreaAccontInfoCapList = [];
                    if ( data._embedded){
                        if (data._embedded["eca-cash-pools"]){                            
                            var tempData = data._embedded["eca-cash-pools"];
                            var topicKey = [];
                            for (var i=0;i<=tempData.length-1;i++){
                                    tempData[i].ecma =[]
                                    tempData[i].ecma=tempData[i].measureInfo?
                                        tempData[i].measureInfo["ECACashPoolMHT.ECACashMeasure"].value:[];
  
                                _self.fundCreaAccontInfoCapList.push(tempData[i]);
                                topicKey.push({"topicKey":'/topic/'+tempData[i].measureInfo["ECACashPoolMHT.ECACashMeasure"].key});
                            }
                            wsocket.connect('/endpointWisely',topicKey,_self.moneySubscribe);
                        }                                                
                    }                                               
                })  
            },
            capitalIn: function(){
                var _self = this;
                _self.loading = true;
                _self.winsigns.ds.POST({url:_self.winsigns.api.fundURL.fundECPools + _self.dlg.dlgId +'/allot-in',
                        data:{"changedCapital":_self.dlg.dlgFundCreaMoney,
                        "matchECACashPoolId":_self.dlg.dlgFundCreaAccount},v:_self},function(data){
                        _self.loading = false; 
                        _self.getFundCreaAccountsInfo(); 
                        _self.dlg.dlgVisible= false;
                        _self.dlg.dlgFundCreaMoney="";
                        _self.$message({
                            message: _self.$t('message.global.success'),
                            type: 'success'
                        });                                             
                    })  
            },
            moneySubscribe: function(message){
                console.log(message)
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
                            'ecma':message.body,
                            'measureInfo':_self.fundCreaAccontInfoCapList[i].measureInfo
                        })
                    } else{
                        tempData.push({
                            'id':_self.fundCreaAccontInfoCapList[i].id,
                            'currency':_self.fundCreaAccontInfoCapList[i].currency,
                            'unassignedCapital':_self.fundCreaAccontInfoCapList[i].unassignedCapital,
                            'ecma':_self.fundCreaAccontInfoCapList[i].ecma,
                            'measureInfo':_self.fundCreaAccontInfoCapList[i].measureInfo
                        })
                    }
                }
                _self.fundCreaAccontInfoCapList = tempData;
            }
        },
        beforeDestroy: function(){
            wsocket.disconnect()
        }
    }
</script>
<style scoped>

</style>