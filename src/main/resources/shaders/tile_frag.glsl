#version 330

in vec2 pass_TextureCoords;

out vec4 out_Color;

uniform sampler2D tileTexture;

void main() {
	vec4 textureColor = texture(tileTexture, pass_TextureCoords);
	if(textureColor.a < 0.5) {
		discard;
	}
	out_Color = textureColor;
}
