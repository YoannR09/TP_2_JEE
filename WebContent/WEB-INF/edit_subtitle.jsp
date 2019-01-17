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
	background-color: white;
}
#top
{
border-radius: 0px;
border-bottom-left-radius: 10px;
border-bottom-right-radius: 10px;
border: 1px groove black;
}

#select, #nom {
	width: 80%;
	border: 1px black solid;
	border-radius: 5px;
}

input {
	height: 40px;
}

#tradeBar {
	background-color: #87CEEB;
	border-radius: 5px;
	border: 1px black solid;
	height: 60px;
}

#ajouter, #lire, #sauvegarder {
	text-align: center;
	background-color: #F0F8FF;
	margin: auto;
	justify-content: space-around;
	border: 1px black solid;
}

#topAjouter, #topLire, #topSauvegarde {
	border-radius: 3px;
}

#table {
	overflow: auto;
	border-radius: 5px;
}

#fichier, #select {
	margin: auto;
}

#fichier {
	display: none;
}

#cadreBot {
	margin-top: 5%;
}

#bloc {
	display: flex;
	justify-content: space-around;
}

#blocTexte {
	height: 60px;
	text-align: center;
	border-radius: 5px;
	border: 1px black solid;
	background-color: #FFFFFF;
}

#blocTemps {
	background-color: #F0F8FF;
	text-align: center;
	border-radius: 5px;
	border: 1px black solid;
	height: 60px;
	font-family: Arial Narrow, sans-serif;
	font-size: 0.9em;
	padding-top: 20px;
}

tr {
	border-spacing: 5px;
}

#indice {
	border: 1px black solid;
}

#blocNum {
	height: 60px;
	text-align: center;
	font-family: impact;
	font-size: 1em;
	background-color: #FF9C2A;
	border: 1px black solid;
	padding-top: 20px;
}

#submit {
	margin: 10px;
}

h2, #tableau {
	text-align: center;
}
</style>
</head>
<body>

	<nav class="navbar navbar-dark bg-primary" id="top">
		<h2
			style="font-size: 2em; font-family: 'Arial Black', Gadget, sans-serif;">Editeur
			de sous-titres</h2>
	</nav>
	<form method="post">

		<%--  Cadre pour ajouter un fichier srt à l'application. L'application
		va lire le fichier et créer des champs vides pour y mettre la
		traduction --%>

		<div class="col-lg-12 col-md-12 col-sm-12" id="bloc">
			<div class="col-lg-4  col-md-4 col-sm-4 ">
				<div class="panel panel-primary" id="ajouter">
					<div class="panel-heading" id="topAjouter">
						<h3 class="panel-title">Ajouter</h3>
					</div>
					<h4>

						<span class="label label-primary"><label for="nom">Nom
								de la vidéo </label></span> <input type="text" name="nom" id="nom" />
					</h4>

					<p>
						<input type="file" name="fichier" id="fichier"
							class="btn btn-primary" /> <a href="#"
							onclick='document.getElementById("fichier").click();'
							class="btn btn-primary btn-primary"><span
							class="glyphicon glyphicon-folder-open"></span> Parcourir</a>
					</p>



					<button class="btn btn-primary" type="submit" id="submit"
						name="submit" value="ajouter">
						<span class="glyphicon glyphicon-plus"></span>
					</button>
				</div>
			</div>


			<%--  Cadre pour lire un fichier déjà enregistrer dans la base de
			données et stocké dans les fichiers --%>

			<div class=" col-lg-4  col-md-4 col-sm-4  ">
				<div class="panel panel-primary" id="lire">
					<div class="panel-heading" id="topLire">
						<h3 class="panel-title">Lire</h3>
					</div>
					<label for="select" class="col-lg-2 control-label"></label> <select
						class="form-control" name="select" id="select">
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


			<%--  Cadre pour sauvegarder la traduction // Les lignes vont êtres
			sauvegardés dans la base de données et un fichier traduction et
			original va être généré. --%>


			<div class="col-lg-4  col-md-4 col-sm-4 ">
				<div class="panel panel-primary" id="sauvegarder">
					<div class="panel-heading" id="topSauvegarde">
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


		<%--  Bloc qui va contenir les sous-titres de la video. // Le numéro de
		la ligne, le temps, le texte original et la traduction seront
		affichés.  --%>

		<div class="col-lg-12  col-md-12 col-sm-12" id="cadreBot">
			<div id="table" class="col-lg-1  col-md-1 col-sm-1">

				<%--  Boucle qui affiche les numéros des lignes. --%>

				<table class="table table-bordered " id="tableau">
					<c:if test="${ not empty numero }">
						<tr>
							<td id="indice" class="bg-primary">N°</td>
						</tr>
					</c:if>
					<c:forEach items="${ numero }" var="num" varStatus="status">

						<tr>

							<td class="bg-primary" id="blocNum"><c:out value="${ num }" /></td>
						</tr>

					</c:forEach>
				</table>
			</div>

			<%--  Boucle qui affiche le temps des lignes. --%>
			<div id="table" class="col-lg-3  col-md-3 col-sm-3">
				<table class="table table-bordered " id="tableau">
					<c:if test="${ not empty temps }">
						<tr>
							<td id="indice" class="bg-primary">Durée du texte</td>
						</tr>
					</c:if>
					<c:forEach items="${ temps }" var="tem" varStatus="status">
						<tr>
							<td class="bg-info" id="blocTemps"><c:out value="${ tem }" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>

			<%--  Boucle qui affiche les lignes du texte original. --%>
			<div id="table" class="col-lg-4  col-md-4 col-sm-4">

				<table class="table table-bordered " id="tableau">
					<c:if test="${ not empty subtitles }">
						<tr>
							<td id="indice" class="bg-primary">Texte original</td>
						</tr>
					</c:if>
					<c:forEach items="${ subtitles }" var="line" varStatus="status">
						<tr>
							<td id="blocTexte"><c:out value="${ line }" /></td>
						</tr>
					</c:forEach>
				</table>

			</div>

			<c:if test="${ submit eq 'lire' }">


				<%--  Boucle qui affiche les lignes pour la traduction. --%>
				<div id="table" class="col-lg-4  col-md-4 col-sm-4">
					<table class="table table-bordered " id="tableau">
						<tr>
							<td id="indice" class="bg-primary">Traduction</td>
						</tr>
						<c:forEach items="${ traduction }" var="lineTrade"
							varStatus="status">
							<tr>
								<td id="tradeBar"><input type="text" size="30"
									name="lineTraductionLire${ status.index }"
									id="lineTradeLire${ status.index }" value="${ lineTrade }" /></td>

							</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
			<c:if test="${ submit eq 'ajouter' }">


				<%--  Boucle qui affiche les lignes pour la traduction. --%>

				<div id="table" class="col-lg-4  col-md-4 col-sm-4">
					<table class="table table-bordered " id="tableau">
						<tr>
							<td id="indice" class="bg-primary">Traduction</td>
						</tr>
						<c:forEach items="${ subtitles }" var="lineTrade"
							varStatus="status">
							<tr>
								<td id="tradeBar"><input type="text"
									name="lineTraductionAjouter${ status.index }" size="30"
									id="lineTradeAjouter${ status.index }" /></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
			<div style="position: fixed; bottom: 1%; left: 47%;">
				<a href="#top" class="btn btn-primary btn-primary"><span
					class="glyphicon glyphicon-arrow-up"></span> </a>
			</div>
		</div>
	</form>
</body>