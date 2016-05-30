package com.hendris;

import com.hendris.winapi.User32;
import com.hendris.winapi.WinAPI;
import com.hendris.winapi.Window;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.PointerByReference;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by hendris on 5/30/16.
 */
public class Teste {

    private static User32 user32 = User32.INSTANCE;

    public static void main(String[] args) {

        WinAPI winAPI = WinAPI.INSTANCE;
        Collection<Window> loadedWindows = winAPI.getLoadedWindows();
        Iterator<Window> iterator = loadedWindows.iterator();
        Window notepad1 = iterator.next();
        Window notepad2 = iterator.next();

        HWND hwnd = notepad1.getHandle();
        HWND hwnd2 = notepad2.getHandle();

        esperar();

        System.out.println(mostrarWindow(hwnd));
        //System.out.println(user32.SetForegroundWindow(hwnd));

        /*esperar();

        System.out.println(user32.SetForegroundWindow(hwnd2));

        esperar();

        System.out.println(user32.SetForegroundWindow(hwnd));*/
    }

    private static boolean mostrarWindow(HWND hwnd) {
        if (user32.IsIconic(hwnd))
            user32.ShowWindow(hwnd, WinUser.SW_RESTORE);

        if (user32.GetForegroundWindow() == hwnd) {
            System.out.println("user32.GetForegroundWindow() == hwnd");
            return true;
        }

        PointerByReference pointer = new PointerByReference();
        int foregroundThreadID = user32.GetWindowThreadProcessId(user32.GetForegroundWindow(), pointer);

        PointerByReference thisPointer = new PointerByReference();
        int thisThreadID = user32.GetWindowThreadProcessId(hwnd, thisPointer);

        if (user32.AttachThreadInput(new DWORD(thisThreadID), new DWORD(foregroundThreadID), true)) {
            user32.BringWindowToTop(hwnd);
            user32.SetForegroundWindow(hwnd);
            user32.AttachThreadInput(new DWORD(thisThreadID), new DWORD(foregroundThreadID), false);
            if (user32.GetForegroundWindow() == hwnd)
                return true;
        }

        /*timeout = ctypes.c_int()
        zero = ctypes.c_int(0)*/
        PointerByReference timeout = new PointerByReference();
        PointerByReference timeoutZero = new PointerByReference();
        timeoutZero.setValue(new Pointer(0));
        System.out.println(user32.SystemParametersInfo(user32.SPI_GETFOREGROUNDLOCKTIMEOUT, 0, timeout, 0));
        System.out.println(user32.SystemParametersInfo(user32.SPI_SETFOREGROUNDLOCKTIMEOUT, 0, timeoutZero, user32.SPIF_SENDCHANGE));
        System.out.println(user32.BringWindowToTop(hwnd));
        System.out.println(user32.SetForegroundWindow(hwnd));
        System.out.println(user32.SystemParametersInfo(user32.SPI_SETFOREGROUNDLOCKTIMEOUT, 0, timeout, user32.SPIF_SENDCHANGE));
        if (user32.GetForegroundWindow() == hwnd)
            return true;

        return false;
    }

    private static void esperar() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
