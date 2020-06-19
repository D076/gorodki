package gorodki;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
//Класс считывающий нажатия кнопок игрока
public class Input extends JComponent{
    private boolean[] map;//массив с ascii значениями кнопок
    
    public Input(){
        map = new boolean[256];
        
        for(int i = 0; i < map.length; i++){
            final int KEY_CODE = i;
            
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, false), i*2);//обработка кнопки, когда она нажата
            getActionMap().put(i*2, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = true;//передаем, что кнопка нажата
                }
            });
            
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, true), i*2 + 1);//обработка кнопки, когда она отпущена
            getActionMap().put(i*2 + 1, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = false;//передаем, что кнопка отпущена
                }
            });
        }
    }
    
    public boolean[] getMap(){
        return Arrays.copyOf(map , map.length);
    }
    
    public boolean getKey(int keyCode){//проверка нажатия какой либо кнопки
        return map[keyCode];//true - если нажата
    }
    
}
