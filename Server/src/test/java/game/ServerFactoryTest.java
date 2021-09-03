package game;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ServerFactoryTest {

    @Test
    public void help_test() {
        String[] args = {"-h"};
        ServerFactory factory = Mockito.spy(new ServerFactory(args));
        factory.startServer();
        Mockito.verify(factory, Mockito.times(1)).showUsageMess();
    }

    @Test
    public void no_args_test() {
        String[] args = {""};
        ServerFactory factory = Mockito.spy(new ServerFactory(args));
        factory.startServer();
        Mockito.verify(factory, times(1)).showUsageMess();
    }

    @Test
    public void invalid_num_args_test() {
        String[] args = {"odd", "num", "args"};
        ServerFactory factory = Mockito.spy(new ServerFactory(args));
        factory.startServer();
        Mockito.verify(factory, times(1)).showUsageMess();
    }

    @Test
    public void invalid_args_test() {
        String[] args = {"invalid", "args", "failing test"};
        ServerFactory factory = Mockito.spy(new ServerFactory(args));
        factory.startServer();
        Mockito.verify(factory, times(1)).showUsageMess();
    }


    @Test
    public void num_format_exception_test() {
        String[] args = {"-p", "invalid_port", "-m", "1"};
        ServerFactory factory = Mockito.spy(new ServerFactory(args));
        factory.startServer();
        Mockito.verify(factory, times(1)).showUsageMess();
    }

    @Test
    public void no_m_arg_test() {
        String[] args = {"-p", "3000"};
        ServerFactory factory = Mockito.spy(new ServerFactory(args));
        factory.startServer();
        Mockito.verify(factory, times(1)).showUsageMess();
    }

    @Test
    public void no_p_arg_test() {
        String[] args = {"-m", "1"};
        ServerFactory factory = Mockito.spy(new ServerFactory(args));
        factory.startServer();
        Mockito.verify(factory, times(1)).showUsageMess();
    }

    @Test
    public void one_player_test() throws IOException {
        String[] args = {"-p", "3000", "-m", "1"};
        ServerFactory factory = Mockito.spy(new ServerFactory(args));
        factory.setOptions(args);
        doNothing().when(factory).acceptOneConnection();
        factory.startAcceptingConnections();
        Mockito.verify(factory, times(1)).acceptOneConnection();
    }

    @Test
    public void two_player_test() throws IOException {
        String[] args = {"-p", "3000", "-m", "2"};
        ServerFactory factory = Mockito.spy(new ServerFactory(args));
        factory.setOptions(args);
        doNothing().when(factory).acceptTwoConnections();
        factory.startAcceptingConnections();
        Mockito.verify(factory, times(1)).acceptTwoConnections();
    }

}
