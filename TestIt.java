import java.io.IOException;
import java.lang.reflect.Method;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class TestIt {

	@JsonProperty(value="withJsonProperty")
	private boolean withJsonProperty;
	@JsonProperty(value="isWithProperty")
	private boolean isWithProperty; //Generate duplicates fields
	@JsonProperty(value="withProp")
	private Boolean withProp;
	@JsonProperty(value="isUsingProp")
	private Boolean isUsingProp;

	@Default
	@JsonProperty(value="withJsonPropertyAndDefault")
	private boolean withJsonPropertyAndDefault= true;
	@Default
	@JsonProperty(value="isWithPropertyAndDefault")
	private boolean isWithPropertyAndDefault= true; //Generate duplicates fields
	@Default
	@JsonProperty(value="withPropAndDefault")
	private Boolean withPropAndDefault= true;
	@Default
	@JsonProperty(value="isUsingPropAndDefault")
	private Boolean isUsingPropAndDefault= true;


	@Default
	private boolean isDefaultingToTrue= true; //does not work if set to false
	@Default
	private boolean defaultingToFalse= false;
	@Default
	private Boolean isDefaultedToTrue= true;
	@Default
	private Boolean defaultedToFalse= false;

	@Default
	@Accessors(fluent = true)
	private Boolean isFluentWithDefaultFail = true; //does not work if set to false
	private boolean online;
	private boolean isBad;  //does not work if set to true
	@Accessors(fluent = true)
	private boolean isBuggy;  //does not work if set to true

	private Boolean isCorrect;
	private Boolean active;
	@Accessors(fluent = true)
	private Boolean isTestingNull; //does not work, I think I over test in this case.

	public static void main(String[] args) throws IOException {

		TestIt defaultTest= TestIt.builder().build();
		for (Method m : defaultTest.getClass().getDeclaredMethods()) {
			System.out.println("Method: " + m);
		}
		System.out.println("\n");
		printTest(defaultTest); //Default is ok, who cares

		testWith(true);
		testWith(false);

	}

	private static void testWith(boolean bool) throws IOException {
		System.out.println("\nTest with: " + bool);
		TestIt in = TestIt.builder()
				.withJsonProperty(bool)
				.isWithProperty(bool)
				.withProp(bool)
				.isUsingProp(bool)
				.withJsonPropertyAndDefault(bool)
				.isWithPropertyAndDefault(bool)
				.withPropAndDefault(bool)
				.isUsingPropAndDefault(bool)
				.online(bool)
				.active(bool)
				.isBad(bool)
				.isCorrect(bool)
				.isTestingNull(bool)
				.isBuggy(bool)
				.isDefaultedToTrue(bool)
				.isDefaultingToTrue(bool)
				.defaultedToFalse(bool)
				.defaultingToFalse(bool)
				.isFluentWithDefaultFail(bool)
				.build();
		printTest(in);
	}

	private static void printTest(TestIt defaultTest) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(defaultTest);
		System.out.println(json);
		TestIt out = objectMapper.readValue(json, TestIt.class);
		System.out.println("In  = " + defaultTest);
		System.out.println("Out = " + out);
	}
}
