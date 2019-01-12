<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet">
<meta charset="utf-8" />
<title>Editer les sous-titres</title>

<style type="text/css">
body {
	background-color: #FFFFFF;
}

#tempsAffichage {
	font-family: Arial Narrow, sans-serif;
}

#tradeBar {
	background-color: #FFF5EE;
}

#ajouter, #lire, #sauvegarder {
	text-align: center;
	background-color: #F0F8FF;
	border-radius: 10px;
	justify-content: space-around;
}

#table {
	overflow: auto;
	border-radius: 5px;
}

#fichier,#select{
	margin: auto;
}
#fichier
{
display :none;
}

#bottom {
	justify-content: space-around;
	text-align: center;
}

#bloc {
	display: flex;
	justify-content: space-around;
}

#submit {
	border-radius: 25px;
	margin: 10px;
}

h2, #tableau {
	text-align: center;
}
</style>
</head>
<body>
	<header class="page-header">
		<h2>Editeur de sous-titres</h2>
	</header>
	<form method="post">
		<div class="col-lg-12 col-md-12 col-sm-12" id="bloc">
			<div class="col-lg-4  col-md-4 col-sm-4 ">
				<div class="panel panel-primary" id="ajouter">
					<div class="panel-heading">
						<h3 class="panel-title">Ajouter</h3>
					</div>
					<h4>
						<span class="label label-primary"><label for="nom">Nom
								de la vidéo : </label></span> <input type="text" name="nom" id="nom" size="10%" />
					</h4>
					
					<p>
					<input type="file" name="fichier" id="fichier"
						class="btn btn-primary" />
					<a href="#" onclick='document.getElementById("fichier").click();' class="btn btn-primary btn-primary"><span class="glyphicon glyphicon-folder-open"></span> Parcourir</a>
					</p>
					


					<button class="btn btn-primary" type="submit" id="submit"
						name="submit" value="ajouter">
						<span class="glyphicon glyphicon-plus"></span>
					</button>
				</div>
			</div>
			<div class=" col-lg-4  col-md-4 col-sm-4  ">
				<div class="panel panel-primary" id="lire">
					<div class="panel-heading">
						<h3 class="panel-title">Lire</h3>
					</div>
					<label for="select" class="col-lg-2 control-label"></label>

						<select class="form-control" name="select" id="select">
							<option value="${ select }" selected>${ select }</option>
							<c:forEach items="${ videos }" var="videoSelect"
								varStatus="status">
								<option value="${ videoSelect.nom }">${ videoSelect.nom }</option>
							</c:forEach>
						</select>

					<button class="btn btn-primary" type="submit" id="submit"
						name="submit" value="lire">
						<span class="glyphicon glyphicon-eye-open"></span>
					</button>
				</div>
			</div>
			<div class="col-lg-4  col-md-4 col-sm-4 ">
				<div class="panel panel-primary" id="sauvegarder">
					<div class="panel-heading">
						<h3 class="panel-title">Sauvegarder</h3>
					</div>
					<div>
						<button class="btn btn-primary" type="submit" id="submit"
							name="submit" value="sauvegarde">
							<span class="glyphicon glyphicon-floppy-disk"></span>
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="col-lg-12  col-md-12 col-sm-12 " id="bottom">
			<div id="table">
				<table class="table table-bordered " id="tableau">
					<tr class="table-primary">

						<c:forEach items="${ numero }" var="num" varStatus="status">

							<td style="height: 10%; text-align: center;" class="bg-primary"
								id="num${ status.index }"><c:out value="${ num }" /></td>
						</c:forEach>

					</tr>
					<tr class="table-primary">

						<c:forEach items="${ temps }" var="tem" varStatus="status">

							<td style="text-align: center; height: 3%;" class="bg-info"
								id="tem${ status.index }"><c:out value="${ tem }" /></td>

						</c:forEach>

					</tr>
					<tr class="table-primary">

						<c:forEach items="${ subtitles }" var="line" varStatus="status">

							<td style="height: 10%; text-align: center;"><c:out
									value="${ line }" /></td>

						</c:forEach>

					</tr>
					<c:if test="${ submit eq 'lire' }">
						<tr class="table-primary">
							<c:forEach items="${ traduction }" var="lineTrade"
								varStatus="status">


								<td style="height: 10%; text-align: center;" id="tradeBar"><input
									type="text" name="lineTraductionLire${ status.index }"
									id="lineTradeLire${ status.index }" size="35"
									value="${ lineTrade }" /></td>


							</c:forEach>
						</tr>
					</c:if>
					<c:if test="${ submit eq 'ajouter' }">
						<tr class="table-primary">
							<c:forEach items="${ subtitles }" var="lineTrade"
								varStatus="status">

								<td style="height: 10%; text-align: center;" id="tradeBar"><input
									type="text" name="lineTraductionAjouter${ status.index }"
									id="lineTradeAjouter${ status.index }" size="35" /></td>
							</c:forEach>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
	</form>
</body>