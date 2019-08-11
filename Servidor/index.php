<!DOCTYPE html>
<html lang="pt-br">

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="refresh" content="30">
    <meta name="author" content="3LT">
    <meta name="description" content="Projeto de TCC.">
    <title>Smart Measurer</title>
    <link rel="stylesheet" href="estilo.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link href='https://fonts.googleapis.com/css?family=Lato:400,300,700' rel='stylesheet' type='text/css'>
    <link rel="icon" href="img/icon.png">
  </head>

  <body>
   <!-- BANNER --> 
    <div class="banner container">
      <div class="title">
        <h2>Consumo de Água e Energia</h2>
        <h3>Bem vindo <br>
        <script type="text/javascript">
         var d = new Date()
         document.write(d.toLocaleString())
        </script> </h3>
      </div>
    </div>

    <!-- SERViÇOS --> 
    <div class="servicos"> 
      <article class="inner servico radius"> 
        <!-- GRÁFICO --> 
        <!-- <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script> -->
        <script type="text/javascript" src="loader.js"></script>
        <script type="text/javascript">
          google.charts.load('current', {'packages':['corechart']});

      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {

        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Hórario');
        data.addColumn('number', 'Água');
        data.addColumn('number', 'Energia');
        data.addRows([
         <?php
              $servidor='localhost';
              $banco='measurer';
              $usuario='root';
              $senha='';
              $link = mysqli_connect($servidor,$usuario,$senha, $banco);
              $yday = date('d-m-Y h:i:s', strtotime("-1 day"));
              $query = "SELECT * FROM medidas WHERE horario > '$yday'";
              $result = mysqli_query($link,$query);
              $row = mysqli_fetch_array($result);
              if ($row) {
                $continue = true;
              } else {
                $continue = false;
               } while ($continue) { 
                $horario=$row['horario'];
                $vazao=$row['vazao'];
                $energia=$row['energia'];
                echo("['$horario', $vazao, $energia]");
                $row = mysqli_fetch_array($result);
              if ($row) {
                $continue = true;
                echo(",\n");
              } else {
                $continue = false;
                echo("\n");
              }
              }
            ?>    
        ]);

        
        var options = {'title':'',
                       'width': '60%',
                       'height':'450'};
        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, options,);
      }
    </script>
            <?php
              ini_set('display_errors', 'On');
              $link = mysqli_connect($servidor,$usuario,$senha, $banco);
              $yday = date('d-m-Y h:i:s', strtotime ("-1 day"));
              $query = "SELECT * FROM medidas WHERE horario > '$yday'";
              $result = mysqli_query($link,$query); 
              $sql = "SELECT FORMAT(SUM(vazao),2) AS total FROM medidas";
              $qry = mysqli_query($link, $sql);
              $row = mysqli_fetch_assoc($qry);
            ?>
            <h4> O consumo total de água é:
              <strong><?php echo $row['total']; ?> Litros </strong>
              <div id="chart_div"></div> </h4>
            <?php
              ini_set('display_errors', 'On');
              $link = mysqli_connect($servidor,$usuario,$senha, $banco);
              $sql = "SELECT FORMAT(SUM(energia),2) AS total FROM medidas";
              $qry = mysqli_query($link,$sql);
              $row = mysqli_fetch_assoc($qry);
            ?>
            <h4> O consumo total de energia é:
              <strong><?php echo $row['total']; ?> W</strong>.
              <div id="chart_div"></div> </h4>

      </article>
      

       <!-- -TABELA --> 
      <article class=" tabela radius">
        <div class="inner">
           <h4> Últimas medidas</h4>
          <table style="width:100%">
            <tr>
              <td><strong>Horário</strong></td>
              <td><strong>Água (L/min)</strong></td>
              <td><strong>Energia</strong></td>
            </tr>
            <?php
              $i=0;
              while ($row = mysqli_fetch_assoc($result)) {
              $horario=$row['horario'];
              $vazao=$row['vazao'];
              $energia=$row['energia'];
            ?>
            <tr>
              <td align="right"><?php echo $horario; ?></td>
                <td align="right"><?php echo $vazao; ?></td>
                  <td align="right"><?php echo $energia; ?></td>
              </tr>
              <?php
                $i++;
                }
              ?>
          </table>       
        </div>      
      </article>
</div>
      <!--RODAPÉ--> 
        <footer class="rodape">
          <div class="social-icons">
           <p class="copyright"> 3lt@etec.sp.gov.br<i class="fa fa-envelope"></i><p class="copyright">Copyright © 3LT 2018.</p></p>
          </div>
        </footer> 
              
  </body>
</html>
