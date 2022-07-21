package mn.hipay.tokenlibrary.callback;

public class Worker {

    CardRemoveListenerCallback  callBack;

    public Worker() {
        //initializing the callback object from the constructor
        this.callBack = null;
    }

    public void setActionListener(CardRemoveListenerCallback  callBack) {
        this.callBack = callBack;

        String successMessage = "";
        Throwable throwableError = new Exception("Some error");
        if (this.callBack!= null) {
            callBack.onSuccess(successMessage);
        }
        else {
            callBack.onFailure(throwableError);
        }
    }
}
