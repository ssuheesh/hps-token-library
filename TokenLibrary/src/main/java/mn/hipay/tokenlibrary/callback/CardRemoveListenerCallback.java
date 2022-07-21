package mn.hipay.tokenlibrary.callback;

public interface CardRemoveListenerCallback
{
    public void onSuccess(String successMessage);
    public void onFailure(Throwable throwableError);
}