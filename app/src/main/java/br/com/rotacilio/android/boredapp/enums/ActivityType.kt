package br.com.rotacilio.android.boredapp.enums

enum class ActivityType {
    ALL,
    EDUCATION,
    RECREATIONAL,
    SOCIAL,
    DIY,
    CHARITY,
    COOKING,
    RELAXATION,
    MUSIC,
    BUSYWORK;

    override fun toString(): String {
        return this.name
    }
}