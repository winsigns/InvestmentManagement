<template> 
    <div>
        <el-row>        
            <div class="line_bottom">
                <el-button type="text" class="float_right right_button"
                @click="openDialog(row)">{{ $t("message.fund.eca_add") }}</el-button>
                <h1>{{ $t("message.fund.eca") }}</h1>
            </div>                
            <div class="line_margin_top"></div>            
        </el-row>
        <el-row>        
            <el-col :span="18" :offset="3">                 
                <el-table v-loading="loading" :data="fundCreaAccontList" style="width: 100%" 
                stripe>
                    <el-table-column sortable
                        prop="ecaTypeShowName"
                        :label=" $t('message.fund.eca_type') ">
                    </el-table-column>  
                    <el-table-column sortable
                        prop="accountNo"
                        :label=" $t('message.fund.eca_no') ">
                    </el-table-column>
                    
                    <el-table-column sortable
                        prop="externalOpenOrganization"
                        :label=" $t('message.fund.eca_finder') ">
                    </el-table-column>  
                    <el-table-column :label=" $t('message.global.operation') ">
                        <template scope="scope">
                            <el-button
                            size="small"
                            type="text"
                            @click="goFundCreaAccountProperties(scope.$index, scope.row)"
                            >{{ $t("message.global.details") }}</el-button>
                            <el-button
                            size="small"
                            @click="handleEdit(scope.$index, scope.row)">{{ $t("message.global.edit") }}</el-button>
                            <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)"
                            >{{ $t("message.global.delete") }}</el-button>
                        </template>
                    </el-table-column>                 
                </el-table>
            </el-col>	                     
        </el-row>  

        <el-dialog :title="dlg.dlgTitle" v-model="dlg.dlgVisible" size="tiny">
            <el-form :model="dlg" label-width="140px">
                <el-form-item :label=" $t('message.fund.eca_type') ">
                    <el-select v-model="dlg.dlgFundCreaAccountType" :placeholder=" $t('message.global.select_tip') ">
                        <el-option
                                v-for="item in captialTypeList"
                                :label="item.displayname"
                                :value="item.name">
                        </el-option>
                    </el-select>
                    <!-- <el-input v-model="dlg.dlgFundCreaAccountType"></el-input>-->
                </el-form-item>      
                 <el-form-item :label=" $t('message.fund.eca_no') ">
                     <el-input v-model="dlg.dlgFundCreaAccountNo"></el-input>
                </el-form-item> 
                 <el-form-item :label=" $t('message.fund.eca_finder') " >
                     <el-input v-model="dlg.dlgFundCreaOrganization"></el-input>
                </el-form-item>          
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dlg.dlgVisible=false">{{ $t("message.global.cancel") }}</el-button>
                <el-button @click="postFundCreaAccount()">{{ $t("message.global.confirm") }}</el-button>
            </div>
        </el-dialog>        
    </div>
</template>
<script>
    export default{       
        data(){
            return {
                fundCreaAccontList: [],
                loading: true,
                //对话框  
                dlg:{
                    dlgTitle: '',
                    dlgFundCreaAccountId:'',
                    dlgFundCreaAccountType:'',
                    dlgFundCreaAccountNo: '',
                    dlgFundCreaOrganization: '0',
                    dlgVisible: false
                },
                captialTypeList: []
            }
        },
        mounted: function(){  
            this.getFundCreaAccounts();
            this.getCaptialType();
        },
        methods:{
            goFundCreaAccountProperties: function(index, row){
                this.$router.push({ name: 'fund-creca-account-info', params: {
                    fundCreacaAccountId: row.id}});                   
            },
             openDialog: function(){
                this.dlg.dlgTitle = this.$t('message.fund.account_add');
                this.dlg.dlgVisible = true; 
                this.dlg.dlgFundCreaAccountId="";
                this.dlg.dlgFundCreaAccountType="";
                this.dlg.dlgFundCreaAccountNo="";    
                this.dlg.dlgFundCreaOrganization="0";
                this.getCaptialType();  
            },
            getFundCreaAccounts: function(){
                var _self = this;
                _self.winsigns.ds.GET({url:_self.winsigns.api.fundURL.funds + '/'+_self.$route.params.fundId+'/external-capital-accounts',
                        data:{}},function(data){
                    _self.loading = false;
                    _self.fundCreaAccontList = data._embedded?data._embedded["external-capital-accounts"]:[];                                                                     
                })  
            },
            postFundCreaAccount: function(){
                var _self = this;
                if (_self.dlg.dlgFundCreaAccountId==''){//新增
                    _self.winsigns.ds.POST({url:_self.winsigns.api.fundURL.funds+_self.$route.params.fundId+'/external-capital-accounts',
                        data:{"accountType":_self.dlg.dlgFundCreaAccountType,
                        "accountNo":_self.dlg.dlgFundCreaAccountNo,
                        "externalOpenOrganization":_self.dlg.dlgFundCreaOrganization}},function(data){
                        _self.getFundCreaAccounts(); 
                        _self.dlg.dlgVisible= false;
                        _self.dlg.dlgFundCreaAccountId="";
                        _self.dlg.dlgFundCreaAccountType="";
                        _self.dlg.dlgFundCreaAccountNo="";    
                        _self.dlg.dlgFundCreaOrganization="0";  
                        _self.$message({
                            message: _self.$t('message.global.success'),
                            type: 'success'
                        });                                             
                    },function(err){
                        console.log(err)
                    })  
                }else{
                    _self.winsigns.ds.PUT({url:_self.winsigns.api.fundURL.fundCreaAccounts+'/'+_self.dlg.dlgFundCreaAccountId,
                        data:{"accountType":_self.dlg.dlgFundCreaAccountType,
                        "accountNo":_self.dlg.dlgFundCreaAccountNo,
                        "externalOpenOrganization":_self.dlg.dlgFundCreaOrganization}},function(data){
                        _self.getFundCreaAccounts(); 
                        _self.dlg.dlgVisible= false;
                        _self.dlg.dlgFundCreaAccountId="";
                        _self.dlg.dlgFundCreaAccountType="";
                        _self.dlg.dlgFundCreaAccountNo="";    
                        _self.dlg.dlgFundCreaOrganization="0";  
                        _self.$message({
                            message: _self.$t('message.global.success'),
                            type: 'success'
                        });                                                    
                    },function(err){
                        
                    })  
                }
            },
            getCaptialType: function () {
                var _self =this;
                console.log( this.captialTypeList)
                _self.winsigns.ds.GET({url:_self.winsigns.api.fundURL.fundEcaTypes,
                    data:{}},function (data) {

                    _self.captialTypeList=
                        data._embedded?data._embedded["eca-types"]:[]
                })
            },
            handleEdit(index, row) {
                this.dlg.dlgTitle = this.$t('message.fund.eca_edit');
                this.dlg.dlgVisible = true; 
                this.dlg.dlgFundCreaAccountId=row.id
                this.dlg.dlgFundCreaAccountType=row.accountType;              
                this.dlg.dlgFundCreaAccountNo=row.accountNo;    
                this.dlg.dlgFundCreaOrganization=row.externalOpenOrganization;
                this.getCaptialType();  
            },
            handleDelete(index, row) {
               var _self = this;
                _self.$confirm(_self.$t('message.fund.eca_delete_confim'), _self.$t('message.global.prompt'), {
                        confirmButtonText: _self.$t('message.global.confirm'),
                        cancelButtonText: _self.$t('message.global.cancel'),
                        type: 'warning'
                    }).then(() => {
                    _self.winsigns.ds.DELETE({url:_self.winsigns.api.fundURL.fundCreaAccounts+'/'+row.id,
                        data:{}},function(data){
                            _self.getFundCreaAccounts();             
                            _self.$message({
                                message: _self.$t('message.global.success'),
                                type: 'success'
                            });                                             
                        },function(err){
                            
                        })                   
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: _self.$t('message.global.cancel_delete')
                        });          
                    });             
            }
        }
    }
</script>
<style scoped>   
    .right_button{
        padding-top:30px;
    }
</style>