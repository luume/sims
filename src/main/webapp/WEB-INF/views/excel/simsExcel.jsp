<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/2.0.8/css/dataTables.dataTables.min.css">

    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/2.0.8/js/dataTables.min.js"></script>

    <!-- Custom fonts for this template -->
    <link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">


</head>

<body>

<div class="container">
    <div class="row">
        <div class="col mt-2">

                <form id="sampleForm" action="${pageContext.request.contextPath}/excel" enctype="multipart/form-data" method="get">
                    <p>
                        <button class="btn btn-primary"
                                onclick="sampleDownload();">양식 다운로드</button>
                    </p>
                </form>

                <p>
                    <input type="file" id="file" name="file" accept=".xlsx, .xls">
                    <button type="button" class="btn btn-primary" id="uploadBtn" onclick="uploadExcel()">업로드</button>
                </p>

            <table id="example" class="display" style="width:100%">
                <thead>
                <tr>
                    <th>EQP_ID</th>
                    <th>관리장비명</th>
                    <th>SIMS접속IP</th>
                    <th>담당자</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

        </div>
    </div>
</div>


<script>

    // 샘플 excel 다운로드
    function sampleDownload(){
        window.open('${pageContext.request.contextPath}/excel', '_blank');
    }

    // 샘플 excel 업로드
    function uploadExcel(){
        let formData = new FormData();
        formData.append('file', $('#file')[0].files[0]);

        $.ajax({
            url: '${pageContext.request.contextPath}/excel/fileUpload',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response, textStatus, xhr) {
                // 업로드 성공 시 처리
                console.log(response);
                console.log(xhr.status);
                for(let i = 0; i < response.length; i++){
                    let str = '<tr><td>' + response[i].eqp_ID + '</td><td>' + response[i].resultCode + '</td><td>' + response[i].ip_ADDRESS + '</td><td>' + response[i].user_ID + '</td></tr>';
                    $("#example").append(str);
                }
                new DataTable('#example', {
                    caption : '실패 목록 리스트'
                });

            },
            error: function(xhr, status, error) {
                let errorMessage = xhr.responseText;
                // 가져온 오류 메시지 사용
                console.error('파일 업로드 실패: ' + errorMessage);

                console.log(errorMessage);

                let ar = errorMessage.split(",");
                console.log(ar.length)

                // 업로드 실패 시 처리
                console.error('파일 업로드 실패 - 상태 코드: ' + xhr.status);
                console.error('파일 업로드 실패 - 상태: ' + status);
                console.error('파일 업로드 실패 - 에러 메시지: ' + error);
            }
        });
    }

</script>


</body>
</html>