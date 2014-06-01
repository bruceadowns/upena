
package com.jivesoftware.os.upena.client;

import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomPanel extends DecoratorPanel {
    Widget[][] custom = new Widget[3][3];
    public CustomPanel(String _styleName) {
        setStyleName(_styleName);
    }
    public void setHeaderLeft(Widget _w) { set(_w,0,0); }
    public void setHeaderCenter(Widget _w) { set(_w,0,1); }
    public void setHeaderRight(Widget _w) { set(_w,0,2); }
    public void setBodyLeft(Widget _w) { set(_w,1,0); }
    //public void setHeaderCenter(Widget _w) { set(_w,0,1); }
    public void setBodyRight(Widget _w) { set(_w,1,2); }
    public void setFooterLeft(Widget _w) { set(_w,2,0); }
    public void setFooterCenter(Widget _w) { set(_w,2,1); }
    public void setFooterRight(Widget _w) { set(_w,2,2); }

    private void set(Widget _w,int _r,int _c) {
        custom[_r][_c] = _w;
        getCellElement(_r, _c).appendChild(_w.getElement());
        adopt(_w);
    }
    
    @Override
    public Iterator<Widget> iterator() {
        final Iterator<Widget> superIterator = super.iterator();
        return new Iterator<Widget>() {
            boolean hl = custom[0][0] != null;
            boolean hc = custom[0][1] != null;
            boolean hr = custom[0][2] != null;
            boolean bl = custom[1][0] != null;
            boolean br = custom[1][2] != null;
            boolean fl = custom[2][0] != null;
            boolean fc = custom[2][1] != null;
            boolean fr = custom[2][2] != null;

            public boolean hasNext() {
                return superIterator.hasNext() || hl || hc || hr || br || bl || fl || fc || fr;
            }

            public Widget next() {
                if (superIterator.hasNext()) {
                    return superIterator.next();
                } else if (hl) {
                    hl = false;
                    return custom[0][0];
                }else if (hc) {
                    hc = false;
                    return custom[0][1];
                }else if (hr) {
                    hr = false;
                    return custom[0][2];
                }else if (bl) {
                    bl = false;
                    return custom[1][0];
                }else if (br) {
                    br = false;
                    return custom[1][2];
                }else if (fl) {
                    fl = false;
                    return custom[2][0];
                }else if (fc) {
                    fc = false;
                    return custom[2][1];
                }else if (fr) {
                    fr = false;
                    return custom[2][2];
                } else {
                    throw new NoSuchElementException();
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


}
