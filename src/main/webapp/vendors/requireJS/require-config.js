/*main.js*/
require.config({
    'baseUrl' : '/vendors',
    'paths' : {
        'jquery' : 'jquery/1.x/jquery.min',//只写文件名，不用带后缀
        'bootstrap' : 'bootstrap/js/bootstrap.min',
        'index' : 'gentelella/js/custom.min',
        'select2':'select2/select2.min',
        'datatable':'datatable/datatables.min'
    },
    'shim' : {
        'bootstrap' : {
            'deps' : ['jquery']
        },
        'index' : {
            'deps' : ['jquery', 'bootstrap']
        },
        'select2':['jquery'],
        'datatable':['jquery']
    }
});