<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form ref="dataForm" >
        <el-row>
          <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
          <#list classInfo.fieldList as fieldItem >
          <el-col :span="4">
            <el-form-item label="${fieldItem.fieldComment}">
              <el-input size="small" v-model="listQuery['${fieldItem.fieldName}']" placeholder="${fieldItem.fieldComment}" style="width: 60%;" class="filter-item" @keyup.enter.native="handleFilter" />
            </el-form-item>
          </el-col>
         
          </#list>
          </#if>
        </el-row>
        
      </el-form>
    </div>

    <el-table
      size="small"
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
     <el-table-column v-for="item in tableColumns" :key="item.accountId" :label="item.label" :prop="item.dataIndex"  align="center" :min-width="item.width" :class-name="getSortClass('id')">
      <template slot-scope="{row}">
          <span>{{ row[item.dataIndex] }}</span>
      </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="300" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
            <!-- 编辑 -->
          </el-button>
          <el-popconfirm
            style="margin-left:5px"
            :confirm-button-text="'确定'"
            :cancel-button-text="'取消'"
            icon="el-icon-info"
            icon-color="red"
            :title="'确定删除该项数据？'" 
            @onConfirm="handleDelete(row,$index)"
          >
            <el-button slot="reference" size="mini" type="danger" >
              删除
              <!-- 删除 -->
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.pageSize" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="80px" style="width: 400px; margin-left:20%;">
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
        <#list classInfo.fieldList as fieldItem >
        <el-form-item label="${fieldItem.fieldComment}" prop="${fieldItem.fieldName}" >
          <el-input v-model="temp.cAccount" placeholder=""/>
        </el-form-item>
        </#list>
        </#if>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消 
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确定 
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import articleService from '@/api/merchant/articleService'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // 分页
import moment from 'moment' //moment 格式化时间
import local from '@/lang/merchant/table/local' //国际化
const viewName = 'i18nView'

 const tableColumns=[
   <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
        {
          label:"${fieldItem.fieldComment}",
          dataIndex:'${fieldItem.fieldName}',
          width:'100'
        }
    </#list>
    </#if>    
  ]



export default {
  name: 'Table',
  components: { Pagination },
  directives: { waves },
  // props: {
  //   queryForm: {
  //     required: false,
  //     type: Array
  //   },
  //   columns:{
  //     required:true,
  //     type:Array
  //   },
  //   data:{
  //     required:true,
  //     tyep:Array
  //   },
  //   editForm: {
  //     required: false,
  //     type: Array
  //   },
  //   listQuery: {   
  //       current: 1,
  //       pageSize: 20,
  //   },
    

  // },
  //定义变量
  data() {    
    return {   
      tableKey: 0,  //列表key
      list: null,  //列表数据
      // total: 0,  //列表数据总数
      listLoading: true, //列表loading状态
      tableColumns, //表格
      fileList:[],//上传文件: 
      operateDate:undefined,
      importanceOptions:[],
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update:'更新',
        create: '新增'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {     //表单规则   true 为必填
        cAccount: [{ required: true, message: 'cAccount is required', trigger: 'change' }],
        mechanismName: [{  required: true, message: 'mechanismName is required', trigger: 'change' }],
        mechanismNo: [{ required: true, message: 'mechanismNo is required', trigger: 'blur' }]
      },
      downloadLoading: false,
      pickerOptions: {   //日期框最近一周、月、三个月
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        }
    }
  },
  watch: {
    lang() {
      this.setOptions()   
    }
  },
  created() {
    //进入页面查询数据
    this.getList()
  },
  methods: {

    //查询数据
    getList() {
      this.listLoading = true
      
      if(this.operateDate){
        let operateDate=this.operateDate;
        this.listQuery.beginDate=moment(operateDate[0]).format('YYYY-MM-DD');
        this.listQuery.endDate=moment(operateDate[1]).format('YYYY-MM-DD');
      }
      articleService.fetchList(this.listQuery).then(response => {
       
        this.list = response.data.data
        this.total = response.data.total
        for(let i in this.list){
          this.list[i].operateDate=moment(this.list[i].operateDate).format("YYYY-MM-DD");
        }

        // Just to simulate the time of the request
        // setTimeout(() => {
          this.listLoading = false
        // }, 1.5 * 1000)
      })
    },
    //点击查询
    handleFilter() {
      this.listQuery.current = 1
      this.getList()
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: ''
      }
    },
    //点击新增按钮事件
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    //新增数据
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.operateDate = moment(this.temp.operateDate).format("YYYY-MM-DD")
          articleService.createMerchant(this.temp).then((res) => {
            // this.list.unshift(this.temp)
            this.getList();
            this.dialogFormVisible = false
            this.$message({
              message: '新增成功',
              type: 'success'
            });
          },(err)=>{
            this.$message.error('新增失败');
          })
        }
      })
    },
    //修改确定按钮
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      // console.log("ddd",row);
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    //修改方法
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          articleService.updateMerchant(tempData).then(() => {
            this.getList();
            this.dialogFormVisible = false
            this.$message({
              message: '修改成功',
              type: 'success'
            });
          },(err)=>{
            this.$message.error('修改失败');
          })
        }
      })
    },
    //删除事件
    handleDelete(row, index) {
      // this.list.splice(index, 1)
      articleService.deleteMerchant(row.accountId).then(() => {
        this.getList();
        this.$message({
          message: '删除成功',
          type: 'success'
        });
      },(err)=>{
        this.$message.error('删除失败');
      })
    },

    //导出
    handleDownload() {
      this.downloadLoading = true
      articleService.downloadMerchant(this.listQuery).then(() => {
        this.getList();
        this.$message({
          message: '导出成功',
          type: 'success'
        });
         this.downloadLoading = false
      },(err)=>{
        this.$message.error('导出失败');
      })
    },
    submitUpload() {
        this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleChange(file,fileList){
      if (fileList.length > 1) {
         fileList.splice(0, 1);
     }
    },

    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>
<style>
.el-input-number .el-input__inner {
  text-align: left;
}
</style>

