<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>开票</title>
    <link rel="stylesheet" type="text/css" href="/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/vendors/select2/select2.min.css"/>
    <style>
        .control-label{
            padding-top: 7px;
            margin-bottom: 0;
            text-align: right;
        }
    </style>
    <script src="/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["layer","select2-cn"],function (main) {
            layer.config({
                path: '/vendors/layer/',
                offset: "100px"
            });
            $(function(){
                var linkState = $("#sysLink").html();
                if(linkState&&linkState == '连接成功'){
                   // $("#printMode").select2();
                }else{
                    layer.alert("开票控件加载失败!")
                }
            })
        })
        function invoiceHandle(result){
            if(result){
                var ss = result.split(",");
                if(ss.length>2&&ss[0].substring(0,2)=="成功"){
                    //成功
                    var invoiceNo = ss[1];
                    parent.updateChargeBill(${chargeBill.id},invoiceNo)
                }else{
                    layer.alert(result,{title:'开票失败',icon:2})
                }
            }


        }
    </script>
<body>
<OBJECT id=DCellWeb1 style="LEFT: 0px; WIDTH: 0px; TOP: 0px; HEIGHT: 0px"
        classid=clsid:7CDB6D1E-CD4A-47B4-BFD0-310B66089007></OBJECT>
<div class="container" >
    <form id="searchForm" class="form-horizontal" role="form">
        <div class="row clearfix">
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-4">连接状态</label>
                <div class="col-xs-8">
                    <h5 id="sysLink"></h5>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-4">卡号</label>
                <div class="col-xs-8">
                    <h5 id="card"></h5>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-4">开票人</label>
                <div class="col-xs-8">
                    <h5 id="handler"></h5>
                </div>
            </div>
        </div>
        <div class="row clearfix">
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-4">票据类型</label>
                <div class="col-xs-8">
                    <h5 id="invoiceType"></h5>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-4">打印模式</label>
                <div class="col-xs-8">
                    <select id="printModeSelect" name="printMode" class="form-control"
                            style="width:100%;">
                        <option value=0>0 显示转入票据信息后再进行开票</option>
                        <option value=1>1 直接开票并打印</option>
                        <option value=2>2 直接开票但不打印</option>
                    </select>
                </div>
            </div>
        </div>
    </form>

    <textarea id="invoiceStyle" class="form-control" rows="10">
<&票据><&票据头>	缴款人或缴款单位=${chargeBill.houseOwner}	资金帐号=${invoiceInfo.invoiceAccount}	物业项目名称=${invoiceInfo.projectName}		地址=${invoiceInfo.address}	物业产业代码=${chargeBill.houseCode}	专户银行名称=${invoiceInfo.invoiceBank}
	</&票据头>
    <&收费项目>
    收费项目=${invoiceInfo.chargeItem}	计费数量=${invoiceInfo.count}	收费标准=${chargeBill.ratio}	金额=${chargeBill.actualSum}
</&收费项目>
</&票据>

    </textarea>
</div>
</body>
<SCRIPT LANGUAGE="vbscript">

        <!--连接-->
      Dim vSign
          vSign=DCellWeb1.PConnect
          if vSign=1 then
            sysLink.innerHTML="连接成功"
            <!--取卡号-->
            Dim vRes,vLen
            vLen=DCellWeb1.PGetCardh(vRes)
            card.innerHTML = Left(vRes,vLen)
            <!--取开票人-->
            vLen=DCellWeb1.PGetKpr(vRes)
            handler.innerHTML=Left(vRes,vLen)
            <!--当前票据类型-->
            vLen=DCellWeb1.PGetCurPj("",vRes)
            invoiceType.innerHTML=Left(vRes,vLen)
          else
            sysLink.innerHTML="连接失败"
          end if
        <!--转入开票-->
        Sub vbInvoice
           Dim vPrnMode,vLen,vStr,vRes
           vPrnMode=document.getElementById("printModeSelect").value
           vStr=invoiceStyle.outerText
           vLen=DCellWeb1.PZrPj(vStr,vPrnMode,"","",vRes)
           invoiceHandle(Left(vRes,vLen))
        End Sub
    </SCRIPT>
</html>
