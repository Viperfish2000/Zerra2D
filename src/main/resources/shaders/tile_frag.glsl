#version 330

in vec2 pass_TextureCoords;

out vec4 out_Color;

uniform sampler2D texture;

void main() {
	out_Color = texture(texture, pass_TextureCoords);
}
