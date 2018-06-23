#version 330

in vec2 pass_TextureCoords;

layout (location = 0) out vec4 out_Color;
layout (location = 1) out vec4 out_BrightColor;

uniform sampler2D entityTexture;
// uniform sampler2D entityGlowTexture;

void main() {
	vec4 textureColor = texture(entityTexture, pass_TextureCoords);
	// vec4 textureGlowColor = texture(tileGlowTexture, pass_TextureCoords);
	if (textureColor.a < 0.5) {
		discard;
	}
	out_Color = textureColor;
	// out_BrightColor = textureGlowColor;
	out_BrightColor = vec4(0.0, 0.0, 0.0, 0.0);
}
