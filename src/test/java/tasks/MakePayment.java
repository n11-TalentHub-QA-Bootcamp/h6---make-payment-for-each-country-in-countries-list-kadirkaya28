package tasks;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SendKeys;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;
import ui.HomePageElements;
import ui.MakePaymentPageElements;

import java.util.List;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class MakePayment implements Task {
    private String name = "";
    private String phone = "";
    private String amount = "";

    public MakePayment(String name, String phone, String amount){
        this.name = name;
        this.phone = phone;
        this.amount = amount;
    }

    @Override
    @Step("{0} user make a payment")
    public <T extends Actor> void performAs(T actor){
        actor.attemptsTo(
                WaitUntil.the(HomePageElements.MAKE_A_PAYMENT_BTN,isClickable()).forNoMoreThan(5).seconds(),
                Click.on(HomePageElements.MAKE_A_PAYMENT_BTN),
                WaitUntil.the(MakePaymentPageElements.PHONE_INPUT_FIELD,isClickable()).forNoMoreThan(5).seconds(),
                Click.on(MakePaymentPageElements.PHONE_INPUT_FIELD),
                SendKeys.of(this.phone).into(MakePaymentPageElements.PHONE_INPUT_FIELD),
                Click.on(MakePaymentPageElements.NAME_INPUT_FIELD),
                SendKeys.of(this.name).into(MakePaymentPageElements.NAME_INPUT_FIELD),
                SendKeys.of(this.amount).into(MakePaymentPageElements.AMOUNT_SLIDER_BAR),
                Click.on(MakePaymentPageElements.SELECT_COUNTRY_BTN)
        );
        int count = 1;
        List<WebElementFacade> countries = MakePaymentPageElements.COUNTRY_LIST.resolveAllFor(actor);
        for (WebElementFacade countryElement : countries){
            if (count == 1){
                actor.attemptsTo(
                        Click.on(countryElement),
                        Click.on(MakePaymentPageElements.SEND_PAYMENT_BTN),
                        WaitUntil.the(MakePaymentPageElements.CONFIRM_SEND_BTN,isClickable()).forNoMoreThan(5).seconds(),
                        Click.on(MakePaymentPageElements.CONFIRM_SEND_BTN)
                );
                count++;
            } else {
                actor.attemptsTo(
                        WaitUntil.the(HomePageElements.MAKE_A_PAYMENT_BTN,isClickable()).forNoMoreThan(5).seconds(),
                        Click.on(HomePageElements.MAKE_A_PAYMENT_BTN),
                        WaitUntil.the(MakePaymentPageElements.PHONE_INPUT_FIELD,isClickable()).forNoMoreThan(5).seconds(),
                        Click.on(MakePaymentPageElements.PHONE_INPUT_FIELD),
                        SendKeys.of(this.phone).into(MakePaymentPageElements.PHONE_INPUT_FIELD),
                        Click.on(MakePaymentPageElements.NAME_INPUT_FIELD),
                        SendKeys.of(this.name).into(MakePaymentPageElements.NAME_INPUT_FIELD),
                        SendKeys.of(this.amount).into(MakePaymentPageElements.AMOUNT_SLIDER_BAR),
                        Click.on(MakePaymentPageElements.SELECT_COUNTRY_BTN),
                        Click.on(countryElement),
                        Click.on(MakePaymentPageElements.SEND_PAYMENT_BTN),
                        WaitUntil.the(MakePaymentPageElements.CONFIRM_SEND_BTN,isClickable()).forNoMoreThan(5).seconds(),
                        Click.on(MakePaymentPageElements.CONFIRM_SEND_BTN)
                );
            }
        }
    }


    public static MakePayment MakePaymentWithInformation(String name, String phone, String amount){
        return instrumented(MakePayment.class,name,phone,amount);
    }
}
