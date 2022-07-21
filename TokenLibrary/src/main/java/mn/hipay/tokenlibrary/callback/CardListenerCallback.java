package mn.hipay.tokenlibrary.callback;

public interface CardListenerCallback
{
    public void onSuccess(String successMessage);
    public void onFailure(Throwable throwableError);
}