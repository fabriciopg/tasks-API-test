package br.sp.fabricio.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8081/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
				.log().all()
			.when()
				.get("/todo")
			.then()
				.log().all()
				.statusCode(200)
			;	
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{\"task\":\"Teste via API\",\"dueDate\":\"2020-12-30\"}")
			.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(201)
			;
	}
	
	@Test
	public void naoDeveAdicionarTarefa() {
		RestAssured.given()
			.body("{\"task\":\"Teste via API\",\"dueDate\":\"2010-12-30\"}")
			.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"))
			;
	}
	
}