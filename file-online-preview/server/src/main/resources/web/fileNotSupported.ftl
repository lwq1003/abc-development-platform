<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="utf-8" />
    <style type="text/css">
        body{
            margin: 0 auto;
            width:900px;

        }
        .container{
            width: 700px;
            height: 700px;
            margin: 0 auto;
        }
        img{
            width:auto;
            height:auto;
            max-width:100%;
            max-height:100%;
            padding-bottom: 36px;

        }
        span{
            display: block;
            font-size:20px;

        }
    </style>
</head>
<body>
<div class="container">

    <span>
    <b>加载文档失败,可能原因如下</b>：
    
        <p style="color: black;">${msg}</p>

    </span>
       <img src="images/failure.svg" />
</div>
</body>

</html>