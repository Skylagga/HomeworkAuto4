import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {


    public String dateOfCreation(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @BeforeEach
    public void openForm() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendValidForm() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input [@placeholder='Город']").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Иванов Иван");
        $x("//input[@name=\"phone\"]").val("+79777777777");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + dateOfMeet), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldSendFormWithInvalidCity() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input [@placeholder='Город']").val("Moscow");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Иванов Иван");
        $x("//input[@name=\"phone\"]").val("+79777777777");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $("[data-test-id='city']").shouldHave(text("Доставка в выбранный город недоступна"));

    }

    @Test
    void shouldSendFormWithInvalidDate() {
        String dateOfMeet = dateOfCreation(2);
        $x("//input [@placeholder='Город']").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Иванов Иван");
        $x("//input[@name=\"phone\"]").val("+79777777777");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $("[data-test-id='date']").shouldHave(text("Заказ на выбранную дату невозможен"));


    }

    @Test
    void shouldSendFormWithInvalidName() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input [@placeholder='Город']").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Ivanov Ivan");
        $x("//input[@name=\"phone\"]").val("+79777777777");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $("[data-test-id='name']").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void shouldSendFormWithInvalidPhoneNumber() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input [@placeholder='Город']").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Иванов Иван");
        $x("//input[@name=\"phone\"]").val("79777777777");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $("[data-test-id='phone']").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void shouldSendFormWithoutCheckbox() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input [@placeholder='Город']").val("Москва");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Иванов Иван");
        $x("//input[@name=\"phone\"]").val("+79777777777");
        $x("//span[@class=\"button__text\"]").click();
        $("[data-test-id='agreement']").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
    @Test
    void shouldSendEmptyForm() {
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//span[@class=\"button__text\"]").click();
        $("[data-test-id='city']").shouldHave(text("Поле обязательно для заполнения"));
    }
}
