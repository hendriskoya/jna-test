package com.hendris;

import com.hendris.winapi.User32;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

/**
 * Created by hendris on 6/2/16.
 */
public class ListenForegroundWindow {

    public static User32.HHOOK hHook;
    public static User32.LowLevelKeyboardProc lpfn;
    public static volatile boolean quit = false;

    public static void main(String[] args) throws InterruptedException {
        listenForegroundWindow();
    }

    private static void listenForegroundWindow() throws InterruptedException {
        /*WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        lpfn = new User32.LowLevelKeyboardProc() {
            public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam,
                                           User32.KBDLLHOOKSTRUCT lParam) {
                System.out.println("here");
                quit = true;
                return User32.INSTANCE.CallNextHookEx(hHook, nCode, wParam, lParam
                        .getPointer());
            }
        };
        hHook = User32.INSTANCE.SetWindowsHookEx(User32.WH_KEYBOARD_LL, lpfn, hMod,
                0);
        if (hHook == null)
            return;
        User32.MSG msg = new User32.MSG();
        while (!quit) {
            User32.INSTANCE.PeekMessage(msg, null, 0, 0, 0);
            Thread.sleep(100);
        }
        if (User32.INSTANCE.UnhookWindowsHookEx(hHook))
            System.out.println("Unhooked");*/

        //*******************************************************

        User32.WinEventProc callback = new User32.WinEventProc() {
            @Override
            public void callback(WinNT.HANDLE hWinEventHook, WinDef.DWORD event, WinDef.HWND hwnd, WinDef.LONG idObject, WinDef.LONG idChild, WinDef.DWORD dwEventThread, WinDef.DWORD dwmsEventTime) {
                System.out.println("passou pelo callback");
            }
        };

        WinNT.HANDLE hook = User32.INSTANCE.SetWinEventHook(EVENT_SYSTEM_FOREGROUND, EVENT_SYSTEM_FOREGROUND, null, callback, 0, 0,
                WINEVENT_OUTOFCONTEXT | WINEVENT_SKIPOWNPROCESS);
        System.out.println("hook: " + hook);

        User32.MSG msg = new User32.MSG();
        int count = 0;
        while (count < 100) {
            User32.INSTANCE.PeekMessage(msg, null, 0, 0, 0);
            Thread.sleep(100);
            count++;
        }

        System.out.println("UnhookWinEvent: " + User32.INSTANCE.UnhookWinEvent(hook));
    }


    static int EVENT_SYSTEM_FOREGROUND = 0x0003;
    static int WINEVENT_OUTOFCONTEXT = 0x0000;
    static int WINEVENT_SKIPOWNPROCESS = 0x0002;
}
