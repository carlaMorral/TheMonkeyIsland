package game;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameFactoryTest {

    @Test
    public void help_arg_option_test(){
        String[] args = {"-h"};
        GameFactory factory = Mockito.spy(new GameFactory(args));
        factory.createGame();
        Mockito.verify(factory, times(1)).showUsageMess();
    }

    @Test
    public void no_args_test() {
        String[] args = {""};
        GameFactory factory = Mockito.spy(new GameFactory(args));
        factory.createGame();
        Mockito.verify(factory, times(1)).showUsageMess();
    }

    @Test
    public void invalid_num_args_test() {
        String[] args = {"odd", "num", "args"};
        GameFactory factory = Mockito.spy(new GameFactory(args));
        factory.createGame();
        Mockito.verify(factory, times(1)).showUsageMess();
    }

    @Test
    public void invalid_args_test() {
        String[] args = {"invalid", "args", "failing test"};
        GameFactory factory = Mockito.spy(new GameFactory(args));
        factory.createGame();
        Mockito.verify(factory, times(1)).showUsageMess();
    }


    @Test
    public void num_format_exception_test() {
        String[] args = {"-s", "127.0.0.1", "-p", "invalid_port"};
        GameFactory factory = Mockito.spy(new GameFactory(args));
        factory.createGame();
        Mockito.verify(factory, times(1)).showUsageMess();
    }

    @Test
    public void no_s_arg_test() {
        String[] args = {"-p", "3000"};
        GameFactory factory = Mockito.spy(new GameFactory(args));
        factory.createGame();
        Mockito.verify(factory, times(1)).showUsageMess();
    }


    @Test
    public void manual_game_created_test() throws IOException {
        String[] args = {"-s", "127.0.0.1", "-p", "3000", "-i", "0"};
        GameFactory factory = Mockito.spy(new GameFactory(args));
        factory.setOptions(args);
        doNothing().when(factory).createManualGame();
        factory.instantiateGame();
        Mockito.verify(factory, times(1)).createManualGame();
    }

    @Test
    public void manual_game_created_default_param_test() throws IOException {
        String[] args = {"-s", "127.0.0.1", "-p", "3000"};
        GameFactory factory = Mockito.spy(new GameFactory(args));
        factory.setOptions(args);
        doNothing().when(factory).createManualGame();
        factory.instantiateGame();
        Mockito.verify(factory, times(1)).createManualGame();
    }

    @Test
    public void automatic_game_created_test() throws IOException {
        String[] args = {"-s", "127.0.0.1", "-p", "3000", "-i", "1"};
        GameFactory factory = Mockito.spy(new GameFactory(args));
        factory.setOptions(args);
        doNothing().when(factory).createAutoGame();
        factory.instantiateGame();
        Mockito.verify(factory, times(1)).createAutoGame();
    }


}
