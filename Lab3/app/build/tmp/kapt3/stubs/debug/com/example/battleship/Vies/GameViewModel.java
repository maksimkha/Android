package com.example.battleship.Vies;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J.\u0010*\u001a\u00020+2\u0006\u0010\u001e\u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200J\u0016\u00101\u001a\u00020+2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200J\u0016\u00102\u001a\u00020+2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200J\u0016\u00103\u001a\u00020+2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200J\u0016\u00104\u001a\u00020+2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200J\u000e\u00105\u001a\u00020+2\u0006\u0010-\u001a\u00020.J\u000e\u00106\u001a\u00020+2\u0006\u0010-\u001a\u00020.J&\u00107\u001a\u00020+2\u0006\u00108\u001a\u00020.2\u0006\u0010/\u001a\u0002002\u0006\u00109\u001a\u00020\u00102\u0006\u0010:\u001a\u00020\u0010J\u0016\u0010;\u001a\u00020+2\u0006\u0010<\u001a\u00020\u00042\u0006\u0010-\u001a\u00020.J\u001e\u0010=\u001a\u00020+2\u0006\u0010>\u001a\u00020?2\u0006\u00108\u001a\u00020.2\u0006\u0010/\u001a\u000200J\u001e\u0010@\u001a\u00020+2\u0006\u0010>\u001a\u00020?2\u0006\u00108\u001a\u00020.2\u0006\u0010/\u001a\u000200J\u001e\u0010A\u001a\u00020+2\u0006\u0010-\u001a\u00020.2\u0006\u00108\u001a\u00020.2\u0006\u0010/\u001a\u000200J\u001e\u0010B\u001a\u00020+2\u0006\u0010>\u001a\u00020?2\u0006\u00108\u001a\u00020.2\u0006\u0010/\u001a\u000200R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R(\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00180\u0018X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001d\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001e\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0012\"\u0004\b \u0010\u0014R\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001a\u0010$\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0012\"\u0004\b&\u0010\u0014R(\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00180\u0018X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001d\u001a\u0004\b(\u0010\u001a\"\u0004\b)\u0010\u001c\u00a8\u0006C"}, d2 = {"Lcom/example/battleship/Vies/GameViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "antiRole", "", "getAntiRole", "()Ljava/lang/String;", "setAntiRole", "(Ljava/lang/String;)V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "getAuth", "()Lcom/google/firebase/auth/FirebaseAuth;", "setAuth", "(Lcom/google/firebase/auth/FirebaseAuth;)V", "cellsInt", "", "getCellsInt", "()I", "setCellsInt", "(I)V", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "finalCells", "", "getFinalCells", "()[[Ljava/lang/Integer;", "setFinalCells", "([[Ljava/lang/Integer;)V", "[[Ljava/lang/Integer;", "gameID", "getGameID", "setGameID", "role", "getRole", "setRole", "ships", "getShips", "setShips", "usedCells", "getUsedCells", "setUsedCells", "GameViewModelConstr", "", "checkFour", "table", "Landroid/widget/TableLayout;", "startBtn", "Landroid/widget/Button;", "checkOne", "checkOther", "checkThree", "checkTwo", "clicks", "gameStart", "setBtnListener", "enemy", "j", "i", "setDef", "am", "setEndRefListener", "ref", "Lcom/google/firebase/database/DatabaseReference;", "setGameStatListener", "setStartBtn", "setTurnListener", "app_debug"})
public final class GameViewModel extends androidx.lifecycle.ViewModel {
    private com.google.firebase.database.FirebaseDatabase database;
    public com.google.firebase.auth.FirebaseAuth auth;
    private int gameID = 0;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String role = "";
    @org.jetbrains.annotations.Nullable()
    private java.lang.String antiRole = "";
    private int ships = 0;
    private int cellsInt = 0;
    @org.jetbrains.annotations.NotNull()
    private java.lang.Integer[][] usedCells;
    @org.jetbrains.annotations.NotNull()
    private java.lang.Integer[][] finalCells;
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.auth.FirebaseAuth getAuth() {
        return null;
    }
    
    public final void setAuth(@org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth p0) {
    }
    
    public final int getGameID() {
        return 0;
    }
    
    public final void setGameID(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRole() {
        return null;
    }
    
    public final void setRole(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAntiRole() {
        return null;
    }
    
    public final void setAntiRole(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final int getShips() {
        return 0;
    }
    
    public final void setShips(int p0) {
    }
    
    public final int getCellsInt() {
        return 0;
    }
    
    public final void setCellsInt(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Integer[][] getUsedCells() {
        return null;
    }
    
    public final void setUsedCells(@org.jetbrains.annotations.NotNull()
    java.lang.Integer[][] p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Integer[][] getFinalCells() {
        return null;
    }
    
    public final void setFinalCells(@org.jetbrains.annotations.NotNull()
    java.lang.Integer[][] p0) {
    }
    
    public final void GameViewModelConstr(int gameID, @org.jetbrains.annotations.NotNull()
    java.lang.String role, @org.jetbrains.annotations.NotNull()
    java.lang.String antiRole, @org.jetbrains.annotations.NotNull()
    com.google.firebase.database.FirebaseDatabase database, @org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth auth) {
    }
    
    public final void gameStart(@org.jetbrains.annotations.NotNull()
    android.widget.TableLayout table) {
    }
    
    public final void checkOne(@org.jetbrains.annotations.NotNull()
    android.widget.TableLayout table, @org.jetbrains.annotations.NotNull()
    android.widget.Button startBtn) {
    }
    
    public final void checkTwo(@org.jetbrains.annotations.NotNull()
    android.widget.TableLayout table, @org.jetbrains.annotations.NotNull()
    android.widget.Button startBtn) {
    }
    
    public final void checkThree(@org.jetbrains.annotations.NotNull()
    android.widget.TableLayout table, @org.jetbrains.annotations.NotNull()
    android.widget.Button startBtn) {
    }
    
    public final void checkFour(@org.jetbrains.annotations.NotNull()
    android.widget.TableLayout table, @org.jetbrains.annotations.NotNull()
    android.widget.Button startBtn) {
    }
    
    public final void checkOther(@org.jetbrains.annotations.NotNull()
    android.widget.TableLayout table, @org.jetbrains.annotations.NotNull()
    android.widget.Button startBtn) {
    }
    
    public final void setDef(@org.jetbrains.annotations.NotNull()
    java.lang.String am, @org.jetbrains.annotations.NotNull()
    android.widget.TableLayout table) {
    }
    
    public final void clicks(@org.jetbrains.annotations.NotNull()
    android.widget.TableLayout table) {
    }
    
    public final void setGameStatListener(@org.jetbrains.annotations.NotNull()
    com.google.firebase.database.DatabaseReference ref, @org.jetbrains.annotations.NotNull()
    android.widget.TableLayout enemy, @org.jetbrains.annotations.NotNull()
    android.widget.Button startBtn) {
    }
    
    public final void setTurnListener(@org.jetbrains.annotations.NotNull()
    com.google.firebase.database.DatabaseReference ref, @org.jetbrains.annotations.NotNull()
    android.widget.TableLayout enemy, @org.jetbrains.annotations.NotNull()
    android.widget.Button startBtn) {
    }
    
    public final void setEndRefListener(@org.jetbrains.annotations.NotNull()
    com.google.firebase.database.DatabaseReference ref, @org.jetbrains.annotations.NotNull()
    android.widget.TableLayout enemy, @org.jetbrains.annotations.NotNull()
    android.widget.Button startBtn) {
    }
    
    public final void setBtnListener(@org.jetbrains.annotations.NotNull()
    android.widget.TableLayout enemy, @org.jetbrains.annotations.NotNull()
    android.widget.Button startBtn, int j, int i) {
    }
    
    public final void setStartBtn(@org.jetbrains.annotations.NotNull()
    android.widget.TableLayout table, @org.jetbrains.annotations.NotNull()
    android.widget.TableLayout enemy, @org.jetbrains.annotations.NotNull()
    android.widget.Button startBtn) {
    }
    
    public GameViewModel() {
        super();
    }
}