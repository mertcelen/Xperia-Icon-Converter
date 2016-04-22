.class public Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;
.super Landroid/app/Activity;
.source "IconPackActivity.java"


# instance fields
.field final EXTRA_PACKAGE_NAME:Ljava/lang/String;

.field final SET_ICON_PACK_ACTION:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 15
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 17
    const-string v0, "com.sonymobile.home.SET_ICON_PACK"

    iput-object v0, p0, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;->SET_ICON_PACK_ACTION:Ljava/lang/String;

    .line 18
    const-string v0, "icon_pack_package_name"

    iput-object v0, p0, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;->EXTRA_PACKAGE_NAME:Ljava/lang/String;

    return-void
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .locals 2
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 22
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 23
    const/high16 v1, 0x7f030000

    invoke-virtual {p0, v1}, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;->setContentView(I)V

    .line 25
    const/high16 v1, 0x7f080000

    invoke-virtual {p0, v1}, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    .line 26
    .local v0, "applyButton":Landroid/widget/Button;
    new-instance v1, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity$1;

    invoke-direct {v1, p0}, Lcom/sonymobile/xperiatesticonpackii/IconPackActivity$1;-><init>(Lcom/sonymobile/xperiatesticonpackii/IconPackActivity;)V

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 39
    return-void
.end method
