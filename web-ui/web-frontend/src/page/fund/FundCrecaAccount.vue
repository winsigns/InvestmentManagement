<template> 
    <div>
        <el-row>        
            <div class="line_bottom">
                <el-button type="text" class="float_right right_button"
                @click="openDialog(row)">添加外部资金账户</el-button>
                <h1>外部资金账户</h1>              
            </div>                
            <div class="line_margin_top"></div>            
        </el-row>
        <el-row>        
            <el-col :span="18" :offset="3">                 
                <el-table v-loading="loading" :data="fundCreaAccontList" style="width: 100%" 
                stripe>
                    <el-table-column sortable
                        prop="ecaTypeShowName"
                        label="外部资金账户类型">
                    </el-table-column>  
                    <el-table-column sortable
                        prop="accountNo"
                        label="外部资金账号">name
                    </el-table-column>
                    
                    <el-table-column sortable
                        prop="externalOpenOrganization"
                        label="开户经纪商">
                    </el-table-column>  
                    <el-table-column label="操作">
                        <template scope="scope">
                            <el-button
                            size="small"
                            type="text"
                            @click="goFundCreaAccountProperties(scope.$index, scope.row)"
                            >详情</el-button> 
                            <el-button
                            size="small"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                            <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)"
                            >删除</el-button>                                                                              
                        </template>
                    </el-table-column>                 
                </el-table>
            </el-col>	                     
        </el-row>  

        <el-dialog :title="dlg.dlgTitle" v-model="dlg.dlgVisible" size="tiny">
            <el-form :model="dlg" label-width="140px">
                <el-form-item label="外部资金账户类型">
                    <el-select v-model="dlg.dlgFundCreaAccountType" placeholder="请选择">
                        <el-option
                                v-for="item in captialTypeList"
                                :label="item.displayname"
                                :value="item.name">
                        </el-option>
                    </el-select>
                    <!-- <el-input v-model="dlg.dlgFundCreaAccountType"></el-input>-->
                </el-form-item>      
                 <el-form-item label="外部资金账户" >
                     <el-input v-model="dlg.dlgFundCreaAccountNo"></el-input>
                </el-form-item> 
                 <el-form-item label="外部开户机构" >
                     <el-input v-model="dlg.dlgFundCreaOrganization"></el-input>
                </el-form-item>          
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dlg.dlgVisible=false">取 消</el-button>
                <el-button @click="postFundCreaAccount()">确 定</el-button>
            </div>
        </el-dialog>        
    </div>
</template>
<script>    
    import api from '../../config/api.json'
    import ds from '../../common/ds'
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
                this.$router.push({ name: 'FundCrecaAccountInfo', params: { 
                    fundCreacaAccountId: row.id}});                   
            },
             openDialog: function(){
                this.dlg.dlgTitle = "添加外部资金账户";
                this.dlg.dlgVisible = true; 
                this.dlg.dlgFundCreaAccountId="";
                this.dlg.dlgFundCreaAccountType="";
                this.dlg.dlgFundCreaAccountNo="";    
                this.dlg.dlgFundCreaOrganization="0";
                this.getCaptialType();  
            },
            getFundCreaAccounts: function(){
                var _self = this;  
                ds.GET({url:api.fundURL.funds + '/'+_self.$route.params.fundId+'/external-capital-accounts',
                        data:{}},function(data){
                    _self.loading = false;
                    _self.fundCreaAccontList = data._embedded?data._embedded["external-capital-accounts"]:[];                                                                     
                })  
            },
            postFundCreaAccount: function(){
                var _self = this;
                if (_self.dlg.dlgFundCreaAccountId==''){//新增
                    ds.POST({url:api.fundURL.funds+_self.$route.params.fundId+'/external-capital-accounts',
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
                            message: '创建外部资金账户成功',
                            type: 'success'
                        });                                             
                    },function(err){
                        console.log(err)
                    })  
                }else{
                    ds.PUT({url:api.fundURL.fundCreaAccounts+'/'+_self.dlg.dlgFundCreaAccountId,
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
                            message: '修改外部资金账户成功',
                            type: 'success'
                        });                                                    
                    },function(err){
                        
                    })  
                }
            },
            getCaptialType: function () {
                /*this.captialTypeList = [
                    { name: 'CHINA_GENERAL_CAPITAL_ACCOUNT', displayname: '普通资金账户' },
                    { name: 'CHINA_FUTURE_CAPITAL_ACCOUNT', displayname: '期货保证金账户' }
                ]*/
                var _self =this;
                console.log( this.captialTypeList)
                ds.GET({url:'fund-service/external-capital-accounts/eca-types',
                    data:{}},function (data) {

                    _self.captialTypeList=
                        data._embedded?data._embedded["eca-types"]:[]
                })
            },
            handleEdit(index, row) {
                this.dlg.dlgTitle = "编辑外部资金账户";
                this.dlg.dlgVisible = true; 
                this.dlg.dlgFundCreaAccountId=row.id
                this.dlg.dlgFundCreaAccountType=row.accountType;              
                this.dlg.dlgFundCreaAccountNo=row.accountNo;    
                this.dlg.dlgFundCreaOrganization=row.externalOpenOrganization;
                this.getCaptialType();  
            },
            handleDelete(index, row) {
               var _self = this;
                _self.$confirm('您正在删除外部资金账户 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        ds.DELETE({url:api.fundURL.fundCreaAccounts+'/'+row.id,
                        data:{}},function(data){
                            _self.getFundCreaAccounts();             
                            _self.$message({
                                message: '删除外部资金账户成功',
                                type: 'success'
                            });                                             
                        },function(err){
                            
                        })                   
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
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