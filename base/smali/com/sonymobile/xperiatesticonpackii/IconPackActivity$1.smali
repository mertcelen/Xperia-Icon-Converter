.class Lcom/sonymobile/xperiatesticonpackii/IconPackActivity$1;
.super Ljava/lang/Object;
.source "IconPackActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;


# direct methods
.method constructor <init>(Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;)V
    .locals 0

    .prologue
    .line 26
    iput-object p1, p0, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity$1;->this$0:Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 5
    .param p1, "v"    # Landroid/view/View;

    .prologue
    .line 29
    new-instance v0, Landroid/content/Intent;

    const-string v2, "com.sonymobile.home.SET_ICON_PACK"

    invoke-direct {v0, v2}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 30
    .local v0, "applyThemeIntent":Landroid/content/Intent;
    const/high16 v2, 0x10000000

    invoke-virtual {v0, v2}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 31
    const-string v2, "icon_pack_package_name"

    iget-object v3, p0, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity$1;->this$0:Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;

    invoke-virtual {v3}, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v2, v3}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 33
    :try_start_0
    iget-object v2, p0, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity$1;->this$0:Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;

    invoke-virtual {v2, v0}, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;->startActivity(Landroid/content/Intent;)V
    :try_end_0
    .catch Landroid/content/ActivityNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    .line 37
    :goto_0
    return-void

    .line 34
    :catch_0
    move-exception v1

    .line 35
    .local v1, "e":Landroid/content/ActivityNotFoundException;
    iget-object v2, p0, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity$1;->this$0:Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;

    const-string v3, "Unable to set theme!"

    const/4 v4, 0x0

    invoke-static {v2, v3, v4}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    goto :goto_0
.end method
