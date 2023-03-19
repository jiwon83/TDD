package chap07Mock.spy;

import chap07Mock.EmailNotifier;

public class SpyEmailNotifier implements EmailNotifier {
    private boolean called;
    private String email;

    public boolean isCalled(){
        return called;
    }
    public String getEmail(){
        return email;
    }

    @Override
    public void sendRegisterEmail(String email) {
        this.email = email;
        this.called = true;
    }
}
